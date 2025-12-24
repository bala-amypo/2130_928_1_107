package com.example.demo.service;

import com.example.demo.model.ClaimRule;

import java.util.List;

public interface ClaimRuleService {

    ClaimRule addRule(ClaimRule rule);

    List<ClaimRule> getAllRules();
}
