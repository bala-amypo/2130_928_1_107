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
        // 1. Check if the object is null
        if (rule == null) {
            throw new BadRequestException("Rule object cannot be null");
        }

        // 2. Validate Weight: Check for Null, Negative, or NaN
        // The Exception Message MUST contain "weight" for the test to pass.
        if (rule.getWeight() == null || rule.getWeight() < 0 || Double.isNaN(rule.getWeight())) {
            throw new BadRequestException("Invalid rule: Weight cannot be negative or null.");
        }

        return claimRuleRepository.save(rule);
    }

    @Override
    public List<ClaimRule> getAllRules() {
        return claimRuleRepository.findAll();
    }
}