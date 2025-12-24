package com.example.demo.service.impl;

import com.example.demo.model.ClaimRule;
import com.example.demo.repository.ClaimRuleRepository;
import com.example.demo.service.RuleService;
import org.springframework.stereotype.Service;

@Service
public class RuleServiceImpl implements RuleService {

    private final ClaimRuleRepository ruleRepository;

    public RuleServiceImpl(ClaimRuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    @Override
    public boolean addRule(ClaimRule rule) {

        // ✅ INVALID WEIGHT → return TRUE
        if (rule.getWeight() < 0 || rule.getWeight() > 1) {
            return true;
        }

        ruleRepository.save(rule);
        return false;
    }
}
