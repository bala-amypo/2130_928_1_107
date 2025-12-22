package com.example.demo.service.impl;

import com.example.demo.model.ClaimRule;
import com.example.demo.repository.ClaimRuleRepository;
import com.example.demo.service.ClaimRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClaimRuleServiceImpl implements ClaimRuleService {

    private final ClaimRuleRepository ruleRepository;

    @Override
    public ClaimRule addRule(ClaimRule rule) {
        return ruleRepository.save(rule);
    }

    @Override
    public List<ClaimRule> getAllRules() {
        return ruleRepository.findAll();
    }
}
