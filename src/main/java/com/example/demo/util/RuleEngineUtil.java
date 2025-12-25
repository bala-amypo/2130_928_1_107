package com.example.demo.util;

import com.example.demo.model.ClaimRule;
import com.example.demo.model.DamageClaim;

import java.util.List;

public class RuleEngineUtil {

    // ------------------------------------------------
    // Used directly by test cases
    // ------------------------------------------------
    public static double computeScore(String description, List<ClaimRule> rules) {

        if (description == null || rules == null || rules.isEmpty()) {
            return 0.0;
        }

        double totalWeight = 0.0;
        double matchedWeight = 0.0;

        for (ClaimRule rule : rules) {

            if (rule.getWeight() <= 0) {
                continue;
            }

            totalWeight += rule.getWeight();

            // ALWAYS rule
            if ("ALWAYS".equalsIgnoreCase(rule.getExpression())) {
                matchedWeight += rule.getWeight();
            }
            // KEYWORD rule
            else if (description.toLowerCase()
                    .contains(rule.getExpression().toLowerCase())) {
                matchedWeight += rule.getWeight();
            }
        }

        if (totalWeight == 0) {
            return 0.0;
        }

        return matchedWeight / totalWeight;
    }

    // ------------------------------------------------
    // Used by DamageClaimService
    // ------------------------------------------------
    public static double evaluate(DamageClaim claim, List<ClaimRule> rules) {

        if (claim == null) {
            return 0.0;
        }

        double score = computeScore(claim.getClaimDescription(), rules);

        if (claim.getAppliedRules() != null && rules != null) {
            claim.getAppliedRules().addAll(rules);
        }

        return score;
    }
}
