package com.placement.service;

import com.placement.model.Opportunity;
import com.placement.model.Student;

public class EligibilityService {

    public static class EligibilityResult {
        private boolean eligible;
        private String reason;

        public EligibilityResult(boolean eligible, String reason) {
            this.eligible = eligible;
            this.reason = reason;
        }

        public boolean isEligible() { return eligible; }
        public String getReason() { return reason; }
    }

    public static EligibilityResult checkEligibility(Student student, Opportunity opp) {
        // 1. Check if profile is completely basic
        if (!student.isProfileCompleted() || student.calculateProfileCompletion() < 100) {
            return new EligibilityResult(false, "Profile is incomplete. Please complete your profile to 100%.");
        }

        // 2. Check CGPA
        if (student.getCgpa() < opp.getMinCgpa()) {
            return new EligibilityResult(false, "CGPA (" + student.getCgpa() + ") is below the required minimum (" + opp.getMinCgpa() + ").");
        }

        // 3. Check Graduation Year
        if (opp.getGraduationYearReq() > 0 && student.getGraduationYear() != opp.getGraduationYearReq()) {
            return new EligibilityResult(false, "Graduation year mismatch. Required: " + opp.getGraduationYearReq() + ", Yours: " + student.getGraduationYear() + ".");
        }

        // 4. Check Branch
        String requiredBranches = opp.getEligibleBranches();
        if (requiredBranches != null && !requiredBranches.trim().isEmpty() && !requiredBranches.equalsIgnoreCase("Any")) {
            boolean branchMatch = false;
            String[] branches = requiredBranches.split(",");
            for (String b : branches) {
                if (b.trim().equalsIgnoreCase(student.getBranch())) {
                    branchMatch = true;
                    break;
                }
            }
            if (!branchMatch) {
                return new EligibilityResult(false, "Your branch (" + student.getBranch() + ") is not eligible. Required: " + requiredBranches + ".");
            }
        }

        return new EligibilityResult(true, "You are eligible to apply.");
    }
}
