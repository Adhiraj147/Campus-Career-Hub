package com.placement.model;

import com.placement.service.EligibilityService.EligibilityResult;

public class RecommendationResult implements Comparable<RecommendationResult> {
    private Opportunity opportunity;
    private int score;            // 0-100
    private String matchReason;   // Human-readable explanation
    private EligibilityResult eligibility;
    private int skillMatchCount;
    private int totalSkillsRequired;

    public RecommendationResult(Opportunity opportunity, int score, String matchReason,
                                EligibilityResult eligibility, int skillMatchCount, int totalSkillsRequired) {
        this.opportunity = opportunity;
        this.score = score;
        this.matchReason = matchReason;
        this.eligibility = eligibility;
        this.skillMatchCount = skillMatchCount;
        this.totalSkillsRequired = totalSkillsRequired;
    }

    @Override
    public int compareTo(RecommendationResult other) {
        return Integer.compare(other.score, this.score); // Higher score first
    }

    // Getters
    public Opportunity getOpportunity() { return opportunity; }
    public int getScore() { return score; }
    public String getMatchReason() { return matchReason; }
    public EligibilityResult getEligibility() { return eligibility; }
    public int getSkillMatchCount() { return skillMatchCount; }
    public int getTotalSkillsRequired() { return totalSkillsRequired; }

    /** Convenience: score band label for UI badges */
    public String getScoreLabel() {
        if (score >= 80) return "Excellent";
        if (score >= 60) return "Good";
        if (score >= 40) return "Fair";
        return "Low";
    }

    public String getScoreBadgeClass() {
        if (score >= 80) return "bg-success";
        if (score >= 60) return "bg-primary";
        if (score >= 40) return "bg-warning text-dark";
        return "bg-secondary";
    }
}
