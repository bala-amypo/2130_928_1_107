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
        // Validation: Must throw exception if weight is negative
        // Message must contain "weight"
        if (rule.getWeight() < 0) {
            throw new BadRequestException("Rule weight cannot be negative");
        }
        return claimRuleRepository.save(rule);
    }

    @Override
    public List<ClaimRule> getAllRules() {
        return claimRuleRepository.findAll();
    }
}