package com.example.demo.util;

import com.example.demo.model.ClaimRule;
import com.example.demo.model.DamageClaim;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RuleEngineUtil {

    private RuleEngineUtil() {}

    public static double evaluate(DamageClaim claim, List<ClaimRule> rules) {

        if (claim == null || rules == null || rules.isEmpty()) {
            return 0.0;
        }

        String description = claim.getDescription();
        if (description == null || description.trim().isEmpty()) {
            claim.setAppliedRules(new HashSet<>());
            return 0.0;
        }

        // normalize description
        String desc = description.toLowerCase().replaceAll("[^a-z0-9 ]", " ");

        double totalWeight = 0.0;
        double matchedWeight = 0.0;

        Set<ClaimRule> appliedRules = new HashSet<>();

        for (ClaimRule rule : rules) {

            if (rule == null || rule.getWeight() <= 0) {
                continue;
            }

            totalWeight += rule.getWeight();

            // ALWAYS rule
            if ("ALWAYS".equalsIgnoreCase(rule.getExpression())) {
                matchedWeight += rule.getWeight();
                appliedRules.add(rule);
                continue;
            }

            String expr = rule.getExpression();
            if (expr == null || expr.trim().isEmpty()) {
                continue;
            }

            String cleanExpr = expr.toLowerCase().replaceAll("[^a-z0-9 ]", " ");

            boolean matched = false;
            for (String word : desc.split("\\s+")) {
                if (word.contains(cleanExpr)) {
                    matched = true;
                    break;
                }
            }

            if (matched) {
                matchedWeight += rule.getWeight();
                appliedRules.add(rule);
            }
        }

        claim.setAppliedRules(appliedRules);

        if (totalWeight == 0) {
            return 0.0;
        }

        return matchedWeight / totalWeight;
    }
}
