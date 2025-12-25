package com.example.demo.util;

import com.example.demo.model.ClaimRule;
import com.example.demo.model.DamageClaim;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RuleEngineUtil {

    // =====================================================
    // METHOD REQUIRED BY TESTS (DO NOT REMOVE)
    // =====================================================
    public static double computeScore(String description, List<?> rules) {

        if (description == null || rules == null || rules.isEmpty()) {
            return 0.0;
        }

        double totalWeight = 0.0;
        double matchedWeight = 0.0;

        String desc = description.toLowerCase();

        for (Object obj : rules) {

            if (!(obj instanceof ClaimRule)) {
                continue;
            }

            ClaimRule rule = (ClaimRule) obj;

            if (rule.getWeight() <= 0) {
                continue;
            }

            totalWeight += rule.getWeight();

            String expr = rule.getExpression();

            // ALWAYS rule
            if ("ALWAYS".equalsIgnoreCase(expr)) {
                matchedWeight += rule.getWeight();
                continue;
            }

            // Keyword match
            if (expr != null && desc.contains(expr.toLowerCase())) {
                matchedWeight += rule.getWeight();
            }
        }

        if (totalWeight == 0) {
            return 0.0;
        }

        return matchedWeight / totalWeight;
    }

    // =====================================================
    // METHOD USED BY SERVICE LAYER
    // =====================================================
    public static double evaluate(DamageClaim claim, List<ClaimRule> rules) {

        if (claim == null || claim.getClaimDescription() == null) {
            return 0.0;
        }

        if (claim.getAppliedRules() == null) {
            claim.setAppliedRules(new HashSet<>());
        }

        double score = computeScore(claim.getClaimDescription(), rules);

        // Track applied rules
        String desc = claim.getClaimDescription().toLowerCase();

        for (ClaimRule rule : rules) {

            if (rule.getWeight() <= 0) continue;

            String expr = rule.getExpression();

            if ("ALWAYS".equalsIgnoreCase(expr)) {
                claim.getAppliedRules().add(rule);
            } else if (expr != null && desc.contains(expr.toLowerCase())) {
                claim.getAppliedRules().add(rule);
            }
        }

        return score;
    }
}
