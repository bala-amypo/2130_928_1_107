package com.example.demo.util;

import com.example.demo.model.ClaimRule;
import com.example.demo.model.DamageClaim;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RuleEngineUtil {

    public static double evaluate(DamageClaim claim, List<ClaimRule> rules) {

        if (rules == null || rules.isEmpty()) {
            claim.setAppliedRules(new HashSet<>());
            return 0.0;
        }

        if (claim.getDescription() == null) {
            claim.setAppliedRules(new HashSet<>());
            return 0.0;
        }

        double totalWeight = 0.0;
        double matchedWeight = 0.0;
        Set<ClaimRule> appliedRules = new HashSet<>();

        String desc = claim.getDescription().toLowerCase();

        for (ClaimRule rule : rules) {
            if (rule.getWeight() < 0) {
                continue;
            }

            totalWeight += rule.getWeight();

            try {
                if (desc.contains(rule.getKeyword().toLowerCase())) {
                    matchedWeight += rule.getWeight();
                    appliedRules.add(rule);
                }
            } catch (Exception e) {
                // invalid rule ignored
            }
        }

        claim.setAppliedRules(appliedRules);

        if (totalWeight == 0) {
            return 0.0;
        }

        return matchedWeight / totalWeight;
    }
}
