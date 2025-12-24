package com.example.demo.controller;

import com.example.demo.model.ClaimRule;
import com.example.demo.service.ClaimRuleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rules")
public class ClaimRuleController {

    private final ClaimRuleService ruleService;

    public ClaimRuleController(ClaimRuleService ruleService) {
        this.ruleService = ruleService;
    }

    @PostMapping
    public ClaimRule addRule(@RequestBody ClaimRule rule) {
        return ruleService.addRule(rule);
    }

    @GetMapping
    public List<ClaimRule> getAll() {
        return ruleService.getAllRules();
    }
}
