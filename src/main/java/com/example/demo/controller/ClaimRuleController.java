package com.example.demo.controller;

import com.example.demo.model.ClaimRule;
import com.example.demo.repository.ClaimRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rules")
@RequiredArgsConstructor
public class ClaimRuleController {

    private final ClaimRuleRepository repository;

    // ✅ POST
    @PostMapping
    public ClaimRule createRule(@RequestBody ClaimRule rule) {
        return repository.save(rule);
    }

    // ✅ GET
    @GetMapping
    public List<ClaimRule> getAllRules() {
        return repository.findAll();
    }
}
