package com.example.demo.util;

import com.example.demo.model.ClaimRule;
import com.example.demo.model.DamageClaim;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RuleEngineUtil {

    // ✅ REQUIRED BY TESTS
    public static double computeScore(String description, List<ClaimRule> rules) {

        if (description == null || rules == null || rules.isEmpty()) {
            return 0.0;
        }

        description = description.toLowerCase();

        double totalWeight = 0.0;
        double matchedWeight = 0.0;

        for (ClaimRule rule : rules) {
            if (rule.getWeight() < 0) continue;

            totalWeight += rule.getWeight();

            if (rule.getKeyword() != null &&
                description.contains(rule.getKeyword().toLowerCase())) {

                matchedWeight += rule.getWeight();
            }
        }

        if (totalWeight == 0) {
            return 0.0;
        }

        return matchedWeight / totalWeight;
    }

    // ✅ USED BY SERVICE
    public static double evaluate(DamageClaim claim, List<ClaimRule> rules) {

        if (claim == null) return 0.0;

        double score = computeScore(claim.getDescription(), rules);

        Set<ClaimRule> applied = new HashSet<>();
        if (claim.getDescription() != null && rules != null) {
            String desc = claim.getDescription().toLowerCase();
            for (ClaimRule r : rules) {
                if (r.getKeyword() != null &&
                    desc.contains(r.getKeyword().toLowerCase())) {
                    applied.add(r);
                }
            }
        }

        claim.setAppliedRules(applied);
        return score;
    }
}
