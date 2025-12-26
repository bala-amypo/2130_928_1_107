package com.example.demo.controller;

import com.example.demo.exception.BadRequestException;
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
    public ResponseEntity<?> addRule(@RequestBody ClaimRule rule) {
        try {
            ClaimRule savedRule = ruleService.addRule(rule);
            return ResponseEntity.ok(savedRule);
        } catch (BadRequestException e) {
            // CRITICAL: Return the exception message string in the body
            // This ensures assertions like content().string(containsString("weight")) pass.
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public List<ClaimRule> getAll() {
        return ruleService.getAllRules();
    }
}