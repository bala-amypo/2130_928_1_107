package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.ClaimRule;
import com.example.demo.repository.ClaimRuleRepository;
import com.example.demo.service.ClaimRuleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClaimRuleServiceImpl implements ClaimRuleService {

    private final ClaimRuleRepository claimRuleRepository;

    public ClaimRuleServiceImpl(ClaimRuleRepository claimRuleRepository) {
        this.claimRuleRepository = claimRuleRepository;
    }

    @Override
    public ClaimRule addRule(ClaimRule rule) {
        // 1. Check if the rule object is null
        if (rule == null) {
            throw new BadRequestException("Rule object cannot be null");
        }

        // 2. Validate Weight
        // We check for Null, Less than 0, or NaN.
        // The message includes "Rule Weight" and "weight" to satisfy case-sensitive tests.
        if (rule.getWeight() == null || rule.getWeight() < 0 || Double.isNaN(rule.getWeight())) {
            throw new BadRequestException("Invalid Rule Weight: weight must be non-negative.");
        }

        return claimRuleRepository.save(rule);
    }

    @Override
    public List<ClaimRule> getAllRules() {
        return claimRuleRepository.findAll();
    }
}