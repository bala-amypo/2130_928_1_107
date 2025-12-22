package com.example.demo.controller;

import com.example.demo.model.ClaimRule;
import com.example.demo.service.ClaimRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rules")
@RequiredArgsConstructor
public class ClaimRuleController {

    private final ClaimRuleService ruleService;

    @PostMapping
    public ClaimRule addRule(@RequestBody ClaimRule rule) {
        return ruleService.addRule(rule);
    }

    @GetMapping
    public List<ClaimRule> getAllRules() {
        return ruleService.getAllRules();
    }
}
