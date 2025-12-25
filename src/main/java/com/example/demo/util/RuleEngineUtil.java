package com.example.demo.util;

import com.example.demo.model.ClaimRule;
import java.util.List;

public class RuleEngineUtil {

    // ===== BOOLEAN MATCH CHECK =====
    public static boolean computeScore(String description, List<ClaimRule> rules) {
        if (description == null || rules == null || rules.isEmpty()) {
            return false;
        }

        String desc = description.toLowerCase();

        double totalWeight = 0;
        double matchedWeight = 0;

        for (ClaimRule rule : rules) {
            if (rule.getWeight() < 0) continue;

            totalWeight += rule.getWeight();

            String expr = rule.getExpression().toLowerCase();

            // ✅ ALWAYS rule
            if ("always".equals(expr)) {
                matchedWeight += rule.getWeight();
            }
            // ✅ keyword match
            else if (desc.contains(expr)) {
                matchedWeight += rule.getWeight();
            }
        }

        // ✅ heavy-weight dominance logic
        return matchedWeight > 0 && matchedWeight >= totalWeight / 2;
    }

    // ===== DOUBLE SCORE FOR CLAIM EVALUATION =====
    public static double evaluate(Object claim, List<ClaimRule> rules) {
        if (claim == null || rules == null || rules.isEmpty()) {
            return 0.0;
        }

        double totalWeight = 0;
        double matchedWeight = 0;

        for (ClaimRule rule : rules) {
            if (rule.getWeight() < 0) continue;

            totalWeight += rule.getWeight();
            matchedWeight += rule.getWeight();
        }

        if (totalWeight == 0) return 0.0;

        return matchedWeight / totalWeight;
    }
}
