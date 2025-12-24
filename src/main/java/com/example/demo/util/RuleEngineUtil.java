package com.example.demo.util;

import com.example.demo.model.ClaimRule;
import com.example.demo.model.DamageClaim;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RuleEngineUtil {

    // Used DIRECTLY by test cases
    public static double computeScore(String description, List<ClaimRule> rules) {

        if (rules == null || rules.isEmpty()) {
            return 0.0;
        }

        double score = 0.0;

        for (ClaimRule rule : rules) {

            String condition = rule.getConditionExpression();
            double weight = rule.getWeight();

            if (condition == null) continue;

            if ("always".equalsIgnoreCase(condition)) {
                score += weight;
            }
            else if (condition.startsWith("description_contains:")) {
                if (description == null) continue;

                String keyword = condition.substring("description_contains:".length());
                if (description.toLowerCase().contains(keyword.toLowerCase())) {
                    score += weight;
                }
            }
        }

        return Math.min(1.0, score);
    }

    // Used by DamageClaimServiceImpl
    public static double evaluate(DamageClaim claim, List<ClaimRule> rules) {

        double score = computeScore(
                claim.getClaimDescription(),
                rules
        );

        Set<ClaimRule> applied = new HashSet<>();

        for (ClaimRule rule : rules) {
            applied.add(rule);
        }

        claim.setScore(score);
        claim.getAppliedRules().clear();
        claim.getAppliedRules().addAll(applied);

        return score;
    }
}
