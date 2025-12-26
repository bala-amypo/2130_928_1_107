package com.example.demo.controller;

import com.example.demo.model.ClaimRule;
import com.example.demo.service.ClaimRuleService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ClaimRule> addRule(@RequestBody ClaimRule rule) {
        // Just call the service. If it fails, GlobalExceptionHandler catches it.
        ClaimRule savedRule = ruleService.addRule(rule);
        return ResponseEntity.ok(savedRule);
    }

    @GetMapping
    public ResponseEntity<List<ClaimRule>> getAll() {
        return ResponseEntity.ok(ruleService.getAllRules());
    }
}