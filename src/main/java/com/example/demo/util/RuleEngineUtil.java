package com.example.demo.util;

import com.example.demo.model.ClaimRule;
import com.example.demo.model.DamageClaim;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RuleEngineUtil {

    // used by service
    public static double evaluate(DamageClaim claim, List<ClaimRule> rules) {

        if (claim == null || rules == null || rules.isEmpty()) {
            claim.setAppliedRules(new HashSet<>());
            return 0.0;
        }

        String description = claim.getDescription();
        if (description == null) {
            claim.setAppliedRules(new HashSet<>());
            return 0.0;
        }

        double totalWeight = 0.0;
        double matchedWeight = 0.0;
        Set<ClaimRule> applied = new HashSet<>();

        for (ClaimRule rule : rules) {
            if (rule.getWeight() < 0) {
                continue;
            }

            totalWeight += rule.getWeight();

            if (rule.getKeyword() != null &&
                description.toLowerCase().contains(rule.getKeyword().toLowerCase())) {

                matchedWeight += rule.getWeight();
                applied.add(rule);
            }
        }

        claim.setAppliedRules(applied);

        if (totalWeight == 0) {
            return 0.0;
        }

        return matchedWeight / totalWeight;
    }

    // used directly by tests
    public static double computeScore(String description, List<ClaimRule> rules) {

        if (description == null || rules == null || rules.isEmpty()) {
            return 0.0;
        }

        double totalWeight = 0.0;
        double matchedWeight = 0.0;

        for (ClaimRule rule : rules) {
            if (rule.getWeight() < 0) {
                continue;
            }

            totalWeight += rule.getWeight();

            if (rule.getKeyword() != null &&
                description.toLowerCase().contains(rule.getKeyword().toLowerCase())) {

                matchedWeight += rule.getWeight();
            }
        }

        if (totalWeight == 0) {
            return 0.0;
        }

        return matchedWeight / totalWeight;
    }
}
