package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.model.ClaimRule;
import com.example.demo.service.ClaimRuleService;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rules")
@Tag(name = "Claim Rules")
public class ClaimRuleController {

    private final ClaimRuleService ruleService;

    public ClaimRuleController(ClaimRuleService ruleService) {
        this.ruleService = ruleService;
    }

    @PostMapping
    @Operation(summary = "Add a new claim rule")
    public ApiResponse addRule(@RequestBody ClaimRule rule) {
        ClaimRule saved = ruleService.addRule(rule);
        return new ApiResponse(true, "Rule added", saved);
    }

    @GetMapping
    @Operation(summary = "Get all claim rules")
    public ApiResponse getAllRules() {
        List<ClaimRule> rules = ruleService.getAllRules();
        return new ApiResponse(true, "Rules fetched", rules);
    }
}
