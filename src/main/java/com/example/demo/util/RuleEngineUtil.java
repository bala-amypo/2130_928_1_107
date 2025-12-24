package com.example.demo.util;

import com.example.demo.model.ClaimRule;
import com.example.demo.model.DamageClaim;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RuleEngineUtil {

    private RuleEngineUtil() {
    }

    public static double evaluate(DamageClaim claim, List<ClaimRule> rules) {

        double score = 0.0;
        Set<ClaimRule> applied = new HashSet<>();

        String description = claim.getClaimDescription() == null
                ? ""
                : claim.getClaimDescription().toLowerCase();

        for (ClaimRule rule : rules) {

            String condition = rule.getConditionExpression();

            boolean matched = false;

            if ("always".equalsIgnoreCase(condition)) {
                matched = true;
            }

            else if (condition != null && condition.startsWith("description_contains:")) {
                String keyword = condition.substring("description_contains:".length()).toLowerCase();
                if (description.contains(keyword)) {
                    matched = true;
                }
            }

            if (matched) {
                score += rule.getWeight();
                applied.add(rule);
            }
        }

        // normalize score between 0 and 1
        if (score > 1.0) {
            score = 1.0;
        }

        claim.getAppliedRules().clear();
        claim.getAppliedRules().addAll(applied);

        return score;
    }
}
