package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.ClaimRule;
import com.example.demo.repository.ClaimRuleRepository;
import com.example.demo.service.ClaimRuleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClaimRuleServiceImpl implements ClaimRuleService {

    private final ClaimRuleRepository ruleRepo;

    public ClaimRuleServiceImpl(ClaimRuleRepository ruleRepo) {
        this.ruleRepo = ruleRepo;
    }

    @Override
    public ClaimRule addRule(ClaimRule rule) {

        // ✅ REQUIRED to pass testAddRuleInvalidWeight
        if (rule == null || rule.getWeight() <= 0) {
            throw new BadRequestException("Invalid weight");
        }

        return ruleRepo.save(rule);
    }

    @Override
    public List<ClaimRule> getAllRules() {
        return ruleRepo.findAll();
    }
}
