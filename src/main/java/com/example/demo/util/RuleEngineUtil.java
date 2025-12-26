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

        String description = claim.getClaimDescription();
        double totalWeight = 0.0;
        double matchedWeight = 0.0;
        Set<ClaimRule> applied = new HashSet<>();

        for (ClaimRule rule : rules) {
            // Robust check including NaN
            if (rule == null || rule.getWeight() == null || rule.getWeight() < 0 || Double.isNaN(rule.getWeight())) continue;

            totalWeight += rule.getWeight();
            boolean match = false;
            String condition = rule.getConditionExpression();

            if (condition != null) {
                // 1. ALWAYS Match
                if ("always".equalsIgnoreCase(condition)) {
                    match = true;
                }
                // 2. Keyword Match (Format: "description_contains:KEYWORD")
                else if (condition.toLowerCase().startsWith("description_contains:")) {
                    String[] parts = condition.split(":", 2);
                    if (parts.length > 1) {
                        String keyword = parts[1].trim();
                        // Case-insensitive check
                        if (description != null && description.toLowerCase().contains(keyword.toLowerCase())) {
                            match = true;
                        }
                    }
                }
            }

            if (match) {
                matchedWeight += rule.getWeight();
                applied.add(rule);
            }
        }

        claim.setAppliedRules(applied);

        if (totalWeight == 0) return 0.0;
        return matchedWeight / totalWeight;
    }

    // REQUIRED HELPER: Used by tests
    public static double computeScore(String description, List<ClaimRule> rules) {
        DamageClaim dummy = new DamageClaim();
        dummy.setClaimDescription(description);
        return evaluate(dummy, rules);
    }
}