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
        if (rule == null) {
            throw new BadRequestException("Rule cannot be null");
        }

        // Fix: Explicitly check for null weight OR negative weight.
        // The exception message MUST contain the word "weight" for the test to pass.
        if (rule.getWeight() == null || rule.getWeight() < 0) {
            throw new BadRequestException("Invalid rule: weight cannot be negative or null");
        }

        return claimRuleRepository.save(rule);
    }

    @Override
    public List<ClaimRule> getAllRules() {
        return claimRuleRepository.findAll();
    }
}