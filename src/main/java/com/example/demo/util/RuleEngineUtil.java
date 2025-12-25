package com.example.demo.util;

import com.example.demo.model.ClaimRule;
import com.example.demo.model.DamageClaim;

import java.util.List;

public class RuleEngineUtil {

    // ---------------------------------------------------
    // RULE ENGINE
    // ---------------------------------------------------
    public static double evaluate(DamageClaim claim, List<ClaimRule> rules) {

        if (claim == null ||
            claim.getClaimDescription() == null ||
            rules == null ||
            rules.isEmpty()) {
            return 0.0;
        }

        String description = claim.getClaimDescription().toLowerCase();

        double totalWeight = 0.0;
        double matchedWeight = 0.0;

        for (ClaimRule rule : rules) {

            if (rule == null || rule.getWeight() <= 0) {
                continue;
            }

            totalWeight += rule.getWeight();

            String expression = rule.getExpression();

            // -------- ALWAYS RULE --------
            if ("ALWAYS".equalsIgnoreCase(expression)) {
                matchedWeight += rule.getWeight();
                claim.getAppliedRules().add(rule);
                continue;
            }

            // -------- KEYWORD MATCH --------
            if (expression != null &&
                description.contains(expression.toLowerCase())) {

                matchedWeight += rule.getWeight();
                claim.getAppliedRules().add(rule);
            }
        }

        if (totalWeight == 0) {
            return 0.0;
        }

        return matchedWeight / totalWeight;
    }
}
