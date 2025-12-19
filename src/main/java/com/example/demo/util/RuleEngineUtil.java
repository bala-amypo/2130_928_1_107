package com.example.demo.util;

import com.example.demo.model.ClaimRule;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class RuleEngineUtil {

    public static double evaluateRules(String claimDescription, List<ClaimRule> rules) {
        double score = 0;

        for (ClaimRule rule : rules) {
            if (claimDescription.contains(rule.getKeyword())) {
                score += rule.getWeight();
            }
        }

        return score;
    }
}
