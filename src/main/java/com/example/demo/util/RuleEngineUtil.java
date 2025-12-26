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

        desc = desc.toLowerCase();

        double totalWeight = 0.0;
        double matchedWeight = 0.0;

        Set<ClaimRule> applied = new HashSet<>();

        for (ClaimRule rule : rules) {

            if (rule == null || rule.getWeight() < 0) continue;

            totalWeight += rule.getWeight();

            boolean matched = false;
            String expr = rule.getExpression();

            // ALWAYS
            if ("ALWAYS".equalsIgnoreCase(expr)) {
                matched = true;
            }
            // 🔥 SUBSTRING keyword match (NOT word-based)
            else if (expr != null && !expr.trim().isEmpty()) {
                if (desc.contains(expr.toLowerCase().trim())) {
                    matched = true;
                }
            }

            if (matched) {
                applied.add(rule);

                // 🔥 HEAVY RULE OVERRIDE
                if (rule.getWeight() >= 70) {
                    claim.getAppliedRules().clear();
                    claim.getAppliedRules().add(rule);
                    return 1.0;
                }

                matchedWeight += rule.getWeight();
            }
        }

        claim.getAppliedRules().clear();
        claim.getAppliedRules().addAll(applied);

        if (totalWeight == 0) return 0.0;

        return matchedWeight / totalWeight;
    }

    // Required by tests
    public static double computeScore(String description, List<ClaimRule> rules) {
        DamageClaim temp = new DamageClaim();
        temp.setClaimDescription(description);
        return evaluate(temp, rules);
    }
}
