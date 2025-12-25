package com.example.demo.service;

import com.example.demo.model.ClaimRule;

public interface RuleService {

    /**
     * @return true if rule is INVALID, false if saved
     */
    boolean addRule(ClaimRule rule);
}
