package com.example.demo.util;

import com.example.demo.model.ClaimRule;
import com.example.demo.model.DamageClaim;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RuleEngineUtil {

    // ---------------- MAIN EVALUATION ----------------
    public static double evaluate(DamageClaim claim, List<ClaimRule> rules) {

        if (claim == null || rules == null || rules.isEmpty()) {
            return 0.0;
        }

        String description = claim.getClaimDescription();
        if (description == null) {
            return 0.0;
        }

        double totalWeight = 0.0;
        double matchedWeight = 0.0;

        Set<ClaimRule> applied = new HashSet<>();

        for (ClaimRule rule : rules) {

            if (rule == null || rule.getWeight() <= 0) continue;

            totalWeight += rule.getWeight();

            String ruleName = rule.getRuleName();

            // ALWAYS rule
            if ("ALWAYS".equalsIgnoreCase(ruleName)) {
                matchedWeight += rule.getWeight();
                applied.add(rule);
            }

            // KEYWORD rule
            else if ("KEYWORD".equalsIgnoreCase(ruleName)) {
                if (rule.getKeyword() != null &&
                        description.toLowerCase().contains(rule.getKeyword().toLowerCase())) {
                    matchedWeight += rule.getWeight();
                    applied.add(rule);
                }
            }
        }

        claim.setAppliedRules(applied);

        if (totalWeight == 0) return 0.0;

        return matchedWeight / totalWeight;
    }

    // ---------------- USED BY TESTS ----------------
    public static boolean computeScore(String description, List<ClaimRule> rules) {

        if (description == null || rules == null || rules.isEmpty()) {
            return false;
        }

        for (ClaimRule rule : rules) {

            String ruleName = rule.getRuleName();

            if ("ALWAYS".equalsIgnoreCase(ruleName)) {
                return true;
            }

            if ("KEYWORD".equalsIgnoreCase(ruleName)
                    && rule.getKeyword() != null
                    && description.toLowerCase().contains(rule.getKeyword().toLowerCase())) {
                return true;
            }
        }

        return false;
    }
}
