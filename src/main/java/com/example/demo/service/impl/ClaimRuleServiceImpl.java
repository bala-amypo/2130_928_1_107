package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.ClaimRule;
import com.example.demo.repository.ClaimRuleRepository;
import com.example.demo.service.ClaimRuleService;

import java.util.List;

public class ClaimRuleServiceImpl implements ClaimRuleService {

    private final ClaimRuleRepository ruleRepo;

    public ClaimRuleServiceImpl(ClaimRuleRepository ruleRepo) {
        this.ruleRepo = ruleRepo;
    }

    @Override
    public ClaimRule addRule(ClaimRule rule) {

        // ✅ FINAL FIX: weight must be > 0
        if (rule.getWeight() <= 0) {
            throw new BadRequestException("Invalid rule: weight must be greater than zero");
        }

        return ruleRepo.save(rule);
    }

    @Override
    public List<ClaimRule> getAllRules() {
        return ruleRepo.findAll();
    }
}
