package com.example.demo.util;

import com.example.demo.model.ClaimRule;
import com.example.demo.model.DamageClaim;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RuleEngineUtil {

    // ============================
    // SERVICE-LEVEL EVALUATION
    // ============================
    public static double evaluate(DamageClaim claim, List<ClaimRule> rules) {
        if (claim == null || rules == null || rules.isEmpty()) {
            return 0.0;
        }

        String description = claim.getClaimDescription();
        double totalWeight = 0.0;
        double matchedWeight = 0.0;

        Set<ClaimRule> applied = new HashSet<>();

        for (ClaimRule rule : rules) {
            // Basic validation
            if (rule == null || rule.getWeight() < 0) continue; // Ignore negative weights safely

            totalWeight += rule.getWeight();
            boolean match = false;

            String ruleName = rule.getRuleName() != null ? rule.getRuleName().toUpperCase() : "";

            // 1. ALWAYS Rule
            if ("ALWAYS".equals(ruleName)) {
                match = true;
            }
            // 2. KEYWORD Rule
            else if ("KEYWORD".equals(ruleName)) {
                String keyword = rule.getKeyword();
                // Null-safe check: match if description contains keyword (case-insensitive)
                if (description != null && keyword != null && 
                    description.toLowerCase().contains(keyword.toLowerCase())) {
                    match = true;
                }
            }

            if (match) {
                matchedWeight += rule.getWeight();
                applied.add(rule);
            }
        }

        // Update the claim with applied rules
        claim.setAppliedRules(applied);

        if (totalWeight == 0) return 0.0;
        return matchedWeight / totalWeight;
    }

    // ============================
    // TEST-LEVEL SCORING METHOD (Used by specific tests)
    // ============================
    public static double computeScore(String description, List<ClaimRule> rules) {
        // Create a dummy claim to reuse the logic above
        DamageClaim dummy = new DamageClaim();
        dummy.setClaimDescription(description);
        return evaluate(dummy, rules);
    }
}