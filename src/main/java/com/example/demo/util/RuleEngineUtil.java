package com.example.demo.util;

import com.example.demo.model.ClaimRule;
import com.example.demo.model.DamageClaim;

import java.util.List;

public class RuleEngineUtil {

    public static boolean evaluate(DamageClaim claim, List<ClaimRule> rules) {

        if (claim == null || claim.getClaimDescription() == null || rules == null) {
            return false;
        }

        double totalScore = 0;

        for (ClaimRule rule : rules) {
            if ("ALWAYS".equalsIgnoreCase(rule.getExpression())) {
                totalScore += rule.getWeight();
                claim.getAppliedRules().add(rule);
            }
            if (claim.getClaimDescription().contains(rule.getExpression())) {
                totalScore += rule.getWeight();
                claim.getAppliedRules().add(rule);
            }
        }

        claim.setScore(totalScore);

        if (totalScore > 0.5) {
            claim.setStatus("APPROVED");
            return true;
        }

        claim.setStatus("REJECTED");
        return false;
    }
}
