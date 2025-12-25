package com.example.demo.util;

import com.example.demo.model.ClaimRule;
import com.example.demo.model.DamageClaim;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RuleEngineUtil {

    // ------------------------------------------------
    // MAIN RULE EVALUATION
    // ------------------------------------------------
    public static double evaluate(DamageClaim claim, List<ClaimRule> rules) {

        if (claim == null || rules == null || rules.isEmpty()) {
            return 0.0;
        }

        String description = claim.getClaimDescription();
        if (description == null || description.trim().isEmpty()) {
            return 0.0;
        }

        description = description.toLowerCase().trim();

        double totalWeight = 0.0;
        double matchedWeight = 0.0;

        Set<ClaimRule> appliedRules = new HashSet<>();

        for (ClaimRule rule : rules) {

            if (rule == null || rule.getWeight() < 0) {
                continue;
            }

            totalWeight += rule.getWeight();

            String expr = rule.getExpression();
            boolean matched = false;

            // ALWAYS rule
            if ("ALWAYS".equalsIgnoreCase(expr)) {
                matched = true;
            }

            // KEYWORD rule (IMPORTANT FIX)
            else if (expr != null && !expr.trim().isEmpty()) {
                matched = description.contains(expr.toLowerCase().trim());
            }

            if (matched) {
                matchedWeight += rule.getWeight();
                appliedRules.add(rule);
            }
        }

        claim.getAppliedRules().clear();
        claim.getAppliedRules().addAll(appliedRules);

        if (totalWeight == 0.0) {
            return 0.0;
        }

        return matchedWeight / totalWeight;
    }

    // ------------------------------------------------
    // TEST HELPER METHOD
    // ------------------------------------------------
    public static double computeScore(String description, List<ClaimRule> rules) {

        DamageClaim temp = new DamageClaim();
        temp.setClaimDescription(description);

        return evaluate(temp, rules);
    }
}
