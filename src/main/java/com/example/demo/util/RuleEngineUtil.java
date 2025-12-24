package com.example.demo.util;

import com.example.demo.model.ClaimRule;

import java.util.ArrayList;
import java.util.List;

public class RuleEngineUtil {

    /**
     * Computes score between 0.0 and 1.0 based on rules
     */
    public static double computeScore(String description, List<ClaimRule> rules) {

        if (rules == null || rules.isEmpty()) {
            return 0.0;
        }

        double totalScore = 0.0;

        for (ClaimRule rule : rules) {

            String condition = rule.getConditionExpression();
            double weight = rule.getWeight();

            if (condition == null) {
                continue;
            }

            // Rule: always
            if ("always".equalsIgnoreCase(condition)) {
                totalScore += weight;
            }

            // Rule: description_contains:KEYWORD
            else if (condition.startsWith("description_contains:")) {

                if (description == null) {
                    continue;
                }

                String keyword = condition.substring("description_contains:".length());

                if (description.toLowerCase().contains(keyword.toLowerCase())) {
                    totalScore += weight;
                }
            }
        }

        // Clamp score between 0 and 1
        return Math.min(1.0, totalScore);
    }
}
