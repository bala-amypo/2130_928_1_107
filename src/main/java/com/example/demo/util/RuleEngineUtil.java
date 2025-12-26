package com.example.demo.util;

import com.example.demo.model.ClaimRule;
import com.example.demo.model.DamageClaim;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RuleEngineUtil {

    public static double evaluate(DamageClaim claim, List<ClaimRule> rules) {

        if (claim == null || rules == null || rules.isEmpty()) {
            return 0.0;
        }

        String desc = claim.getClaimDescription();
        if (desc == null || desc.trim().isEmpty()) {
            return 0.0;
        }

        desc = desc.toLowerCase().replaceAll("[^a-z0-9 ]", " ");

        double totalWeight = 0.0;
        double matchedWeight = 0.0;

        Set<ClaimRule> applied = new HashSet<>();

        for (ClaimRule rule : rules) {

            if (rule == null || rule.getWeight() <= 0) continue;

            totalWeight += rule.getWeight();

            boolean matched = false;
            String expr = rule.getExpression();

            // ALWAYS rule
            if ("ALWAYS".equalsIgnoreCase(expr)) {
                matched = true;
            }
            // 🔥 WORD-LEVEL MATCH
            else if (expr != null && !expr.trim().isEmpty()) {

                String cleanExpr = expr.toLowerCase().replaceAll("[^a-z0-9 ]", " ");

                for (String word : desc.split("\\s+")) {
                    if (word.contains(cleanExpr)) {
                        matched = true;
                        break;
                    }
                }
            }

            if (matched) {
                applied.add(rule);
                matchedWeight += rule.getWeight();
            }
        }

        claim.getAppliedRules().clear();
        claim.getAppliedRules().addAll(applied);

        if (totalWeight == 0) return 0.0;

        return matchedWeight / totalWeight;
    }

    // REQUIRED BY TESTS
    public static double computeScore(String description, List<ClaimRule> rules) {
        DamageClaim temp = new DamageClaim();
        temp.setClaimDescription(description);
        return evaluate(temp, rules);
    }
}
