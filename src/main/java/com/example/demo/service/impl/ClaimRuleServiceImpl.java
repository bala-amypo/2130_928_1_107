package com.example.demo.service.impl;

import com.example.demo.model.ClaimRule;
import com.example.demo.repository.ClaimRuleRepository;
import com.example.demo.service.ClaimRuleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClaimRuleServiceImpl implements ClaimRuleService {

    private final ClaimRuleRepository repository;

    public ClaimRuleServiceImpl(ClaimRuleRepository repository) {
        this.repository = repository;
    }

    @Override
    public ClaimRule addRule(ClaimRule rule) {

        if (rule == null || rule.getWeight() <= 0) {
            throw new IllegalArgumentException("Invalid rule weight");
        }

        return repository.save(rule);
    }

    @Override
    public List<ClaimRule> getAllRules() {
        return repository.findAll();
    }
}
