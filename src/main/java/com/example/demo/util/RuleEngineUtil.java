package com.example.demo.util;

import com.example.demo.model.ClaimRule;
import com.example.demo.model.DamageClaim;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RuleEngineUtil {

    // Used by service
    public static double evaluate(DamageClaim claim, List<ClaimRule> rules) {
        if (claim == null) return 0.0;
        return computeScore(claim.getClaimDescription(), rules, claim);
    }

    // Used by tests
    public static double computeScore(String description, List<?> rules) {
        return computeScore(description, (List<ClaimRule>) rules, null);
    }

    private static double computeScore(
            String description,
            List<ClaimRule> rules,
            DamageClaim claim
    ) {

        if (rules == null || rules.isEmpty()) return 0.0;
        if (description == null) description = "";

        description = description.toLowerCase();

        String[] words = description.split("\\s+");

        double totalWeight = 0.0;
        double matchedWeight = 0.0;

        Set<ClaimRule> applied = new HashSet<>();

        for (ClaimRule rule : rules) {
            if (rule == null || rule.getWeight() <= 0) continue;

            totalWeight += rule.getWeight();

            String expr = rule.getExpression();
            if (expr == null) continue;

            expr = expr.toLowerCase();

            boolean matched = false;

            if ("always".equals(expr)) {
                matched = true;
            } else {
                for (String word : words) {
                    if (word.contains(expr)) {
                        matched = true;
                        break;
                    }
                }
            }

            if (matched) {
                matchedWeight += rule.getWeight();
                applied.add(rule);
            }
        }

        if (claim != null) {
            claim.setAppliedRules(applied);
        }

        if (totalWeight == 0) return 0.0;

        return matchedWeight / totalWeight;
    }
}
