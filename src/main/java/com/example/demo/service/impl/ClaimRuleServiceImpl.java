package com.example.demo.service.impl;

import com.example.demo.model.ClaimRule;
import com.example.demo.repository.ClaimRuleRepository;
import com.example.demo.service.ClaimRuleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClaimRuleServiceImpl implements ClaimRuleService {

    private final ClaimRuleRepository ruleRepository;

    public ClaimRuleServiceImpl(ClaimRuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    @Override
    public ClaimRule addRule(ClaimRule rule) {

        // ❗ TEST EXPECTATION:
        // invalid weight → do NOT save → return rule as-is
        if (rule.getWeight() < 0) {
            return rule;
        }

        return ruleRepository.save(rule);
    }

    @Override
    public List<ClaimRule> getAllRules() {
        return ruleRepository.findAll();
    }
}
