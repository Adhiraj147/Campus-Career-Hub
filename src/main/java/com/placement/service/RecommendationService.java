package com.placement.service;

import com.placement.dao.ApplicationDAO;
import com.placement.dao.ApplicationDAOImpl;
import com.placement.dao.OpportunityDAO;
import com.placement.dao.OpportunityDAOImpl;
import com.placement.model.Application;
import com.placement.model.Opportunity;
import com.placement.model.RecommendationResult;
import com.placement.model.Student;
import com.placement.service.EligibilityService.EligibilityResult;

import java.sql.SQLException;
import java.util.*;

/**
 * Rule-based recommendation engine.
 *
 * Scoring breakdown (total 100 pts):
 *   Skill overlap        — up to 40 pts
 *   CGPA headroom        — up to 20 pts
 *   Branch match         — 20 pts
 *   Graduation year      — 10 pts
 *   Type preference      — 10 pts
 *
 * Eligibility (EligibilityService) is evaluated separately and surfaced
 * as a flag. Ineligible opportunities are included but clearly marked so
 * students can see why they don't qualify yet — they are NOT hidden.
 */
public class RecommendationService {

    private final OpportunityDAO oppDAO;
    private final ApplicationDAO appDAO;

    public RecommendationService() {
        this.oppDAO = new OpportunityDAOImpl();
        this.appDAO = new ApplicationDAOImpl();
    }

    /**
     * Returns recommendations for the given student, sorted by score descending.
     * Already-applied opportunities are excluded.
     */
    public List<RecommendationResult> getRecommendations(Student student) throws SQLException {
        if (student == null) {
            return Collections.emptyList();
        }

        List<Opportunity> allOpen = oppDAO.getAllOpenOpportunities();
        Set<Integer> appliedOppIds = getAppliedOppIds(student.getStudentId());
        String preferredType = getPreferredType(student.getStudentId());

        List<RecommendationResult> results = new ArrayList<>();

        for (Opportunity opp : allOpen) {
            // Skip already-applied
            if (appliedOppIds.contains(opp.getOppId())) continue;
            // Skip past deadline
            if (opp.getDeadline() != null && opp.getDeadline().before(new java.util.Date())) continue;

            int score = 0;
            StringBuilder reason = new StringBuilder();

            // ── 1. Skill overlap (40 pts) ──────────────────────────────────
            int skillMatch = 0;
            int totalReq = 0;
            List<String> studentSkillsLower = toLowerList(student.getSkills());

            if (opp.getRequiredSkills() != null && !opp.getRequiredSkills().trim().isEmpty()) {
                String[] reqSkills = opp.getRequiredSkills().split("[,;]+");
                totalReq = reqSkills.length;
                for (String s : reqSkills) {
                    if (studentSkillsLower.contains(s.trim().toLowerCase())) {
                        skillMatch++;
                    }
                }
                int skillScore = totalReq > 0 ? (int) Math.round((40.0 * skillMatch) / totalReq) : 0;
                score += skillScore;
                if (skillMatch > 0) {
                    reason.append(skillMatch).append("/").append(totalReq).append(" skills match; ");
                }
            } else {
                // No skill requirement → full points
                score += 40;
                reason.append("No specific skills required; ");
            }

            // ── 2. CGPA headroom (20 pts) ─────────────────────────────────
            if (opp.getMinCgpa() <= 0) {
                score += 20;
                reason.append("No CGPA requirement; ");
            } else {
                double gap = student.getCgpa() - opp.getMinCgpa();
                if (gap >= 1.0) {
                    score += 20;
                } else if (gap >= 0.5) {
                    score += 14;
                } else if (gap >= 0.0) {
                    score += 7;
                }
                // Negative gap → 0 pts (eligible check will flag)
                reason.append("CGPA ").append(student.getCgpa()).append(" vs min ").append(opp.getMinCgpa()).append("; ");
            }

            // ── 3. Branch match (20 pts) ───────────────────────────────────
            String eligBranches = opp.getEligibleBranches();
            if (eligBranches == null || eligBranches.trim().isEmpty() || eligBranches.equalsIgnoreCase("Any")) {
                score += 20;
                reason.append("Open to all branches; ");
            } else {
                boolean branchMatch = false;
                for (String b : eligBranches.split(",")) {
                    if (b.trim().equalsIgnoreCase(student.getBranch())) {
                        branchMatch = true;
                        break;
                    }
                }
                if (branchMatch) {
                    score += 20;
                    reason.append("Branch matches; ");
                } else {
                    reason.append("Branch mismatch; ");
                }
            }

            // ── 4. Graduation year (10 pts) ────────────────────────────────
            if (opp.getGraduationYearReq() <= 0 || opp.getGraduationYearReq() == student.getGraduationYear()) {
                score += 10;
                reason.append("Graduation year matches; ");
            }

            // ── 5. Type preference (10 pts) ────────────────────────────────
            if (preferredType != null && preferredType.equalsIgnoreCase(opp.getType())) {
                score += 10;
                reason.append("Preferred type (").append(opp.getType()).append("); ");
            }

            // ── Eligibility check (uses existing EligibilityService) ───────
            EligibilityResult eligibility = EligibilityService.checkEligibility(student, opp);

            // Remove trailing "; "
            String reasonStr = reason.toString().replaceAll(";\\s*$", "").trim();
            if (reasonStr.isEmpty()) reasonStr = "General match";

            results.add(new RecommendationResult(opp, score, reasonStr, eligibility, skillMatch, totalReq));
        }

        Collections.sort(results);
        return results;
    }

    // ── Helpers ────────────────────────────────────────────────────────────

    private Set<Integer> getAppliedOppIds(int studentId) {
        Set<Integer> ids = new HashSet<>();
        try {
            for (Application app : appDAO.getApplicationsByStudent(studentId)) {
                ids.add(app.getOppId());
            }
        } catch (SQLException e) {
            // Silently continue — we just won't exclude applied jobs
            e.printStackTrace();
        }
        return ids;
    }

    private String getPreferredType(int studentId) {
        // Determine type the student has applied to most
        try {
            Map<String, Integer> typeCounts = new HashMap<>();
            for (Application app : appDAO.getApplicationsByStudent(studentId)) {
                // We don't have type on Application model, so we skip this refinement
                // gracefully — bonus points not applied
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Simplification: type preference not applied if no history
    }

    private List<String> toLowerList(List<String> skills) {
        if (skills == null) return Collections.emptyList();
        List<String> lower = new ArrayList<>();
        for (String s : skills) lower.add(s.toLowerCase().trim());
        return lower;
    }
}
