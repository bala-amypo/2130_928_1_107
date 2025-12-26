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
            // Try to add the rule
            ClaimRule savedRule = ruleService.addRule(rule);
            return ResponseEntity.ok(savedRule);
        } catch (BadRequestException e) {
            // CRITICAL FIX: Catch the exception from the service 
            // and return the message (containing "weight") with 400 Bad Request.
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public List<ClaimRule> getAll() {
        return ruleService.getAllRules();
    }
}