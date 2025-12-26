package com.example.demo.service.impl;

import com.example.demo.model.ClaimRule;
import com.example.demo.repository.ClaimRuleRepository;
import com.example.demo.service.ClaimRuleService;

public class ClaimRuleServiceImpl implements ClaimRuleService {
    private ClaimRuleRepository ruleRepo;

    public ClaimRuleServiceImpl(ClaimRuleRepository ruleRepo) {
        this.ruleRepo = ruleRepo;
    }

    @Override
    public ClaimRule addRule(ClaimRule rule) throws Exception {
        if (rule.getWeight() < 0) {
            throw new Exception("Weight cannot be negative");
        }
        return ruleRepo.save(rule);
    }
}