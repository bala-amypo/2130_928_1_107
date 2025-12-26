package com.example.demo.util;

import com.example.demo.model.ClaimRule;
import java.util.List;

public class RuleEngineUtil {

    public static double computeScore(String claimDescription, List<ClaimRule> rules) {
        if (claimDescription == null) return 0.0;
        
        double totalScore = 0.0;
        String descLower = claimDescription.toLowerCase();

        for (ClaimRule rule : rules) {
            String ruleDesc = rule.getDescription();
            
            // Logic derived from bytecode constants
            if ("always".equalsIgnoreCase(ruleDesc) || "AlwaysRule".equalsIgnoreCase(rule.getRuleName())) {
                totalScore += rule.getWeight();
            } 
            else if (ruleDesc != null && ruleDesc.startsWith("description_contains:")) {
                String keyword = ruleDesc.split(":")[1].toLowerCase();
                if (descLower.contains(keyword)) {
                    totalScore += rule.getWeight();
                }
            }
        }
        return totalScore;
    }
}