package com.example.demo.util;

import com.example.demo.model.ClaimRule;
import com.example.demo.model.DamageClaim;

import java.util.List;

public class RuleEngineUtil {

    // USED BY MANY TESTS
    public static double computeScore(String description, List<?> rules) {

        if (description == null || rules == null || rules.isEmpty()) {
            return 0.0;
        }

        double total = 0.0;

        for (Object obj : rules) {
            if (obj instanceof ClaimRule rule) {
                if (description.toLowerCase()
                        .contains(rule.getExpression().toLowerCase())) {
                    total += rule.getWeight();
                }
            }
        }
        return total;
    }

    // USED BY SERVICE
    public static double evaluate(DamageClaim claim, List<ClaimRule> rules) {

        if (claim == null || claim.getClaimDescription() == null) {
            return 0.0;
        }

        double score = computeScore(
                claim.getClaimDescription(), rules);

        claim.getAppliedRules().clear();

        for (ClaimRule rule : rules) {
            if (claim.getClaimDescription()
                    .toLowerCase()
                    .contains(rule.getExpression().toLowerCase())) {
                claim.getAppliedRules().add(rule);
            }
        }

        return score;
    }
}
