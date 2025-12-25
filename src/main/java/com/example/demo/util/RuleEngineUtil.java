package com.example.demo.util;

import com.example.demo.model.ClaimRule;
import com.example.demo.model.DamageClaim;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RuleEngineUtil {

    // -------------------------------------------------
    // MAIN EVALUATION METHOD
    // -------------------------------------------------
    public static double evaluate(DamageClaim claim, List<ClaimRule> rules) {

        if (claim == null || rules == null || rules.isEmpty()) {
            return 0.0;
        }

        // ✅ CORRECT FIELD NAME
        String description = claim.getClaimDescription();
        if (description == null) {
            return 0.0;
        }

        double totalWeight = 0.0;
        double matchedWeight = 0.0;

        Set<ClaimRule> appliedRules = new HashSet<>();

        for (ClaimRule rule : rules) {

            if (rule == null || rule.getWeight() <= 0) {
                continue;
            }

            totalWeight += rule.getWeight();

            boolean matched = false;

            // ✅ ALWAYS rule
            if ("ALWAYS".equalsIgnoreCase(rule.getExpression())) {
                matched = true;
            }

            // ✅ KEYWORD match (case-insensitive)
            else if (rule.getExpression() != null &&
                    description.toLowerCase().contains(rule.getExpression().toLowerCase())) {
                matched = true;
            }

            if (matched) {
                matchedWeight += rule.getWeight();
                appliedRules.add(rule);
            }
        }

        // ✅ STORE applied rules
        claim.getAppliedRules().clear();
        claim.getAppliedRules().addAll(appliedRules);

        if (totalWeight == 0.0) {
            return 0.0;
        }

        return matchedWeight / totalWeight;
    }

    // -------------------------------------------------
    // TEST-ONLY METHOD (REQUIRED)
    // -------------------------------------------------
    public static double computeScore(String description, List<ClaimRule> rules) {

        DamageClaim temp = new DamageClaim();
        temp.setClaimDescription(description); // ✅ FIXED

        return evaluate(temp, rules);
    }
}
