package com.example.demo.service.impl;

import com.example.demo.model.ClaimRule;
import com.example.demo.repository.ClaimRuleRepository;
import com.example.demo.service.ClaimRuleService;
import org.springframework.stereotype.Service;

@Service
public class ClaimRuleServiceImpl implements ClaimRuleService {

    private final ClaimRuleRepository repository;

    public ClaimRuleServiceImpl(ClaimRuleRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean addRule(ClaimRule rule) {

        // ✅ FIX: double cannot be null
        if (rule.getWeight() < 0) {
            return false;
        }

        repository.save(rule);
        return true;
    }
}
