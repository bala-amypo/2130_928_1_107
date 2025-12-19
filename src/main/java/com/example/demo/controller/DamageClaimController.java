package com.example.demo.controller;

import com.example.demo.model.DamageClaim;
import com.example.demo.service.DamageClaimService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/claims")
@RequiredArgsConstructor
public class DamageClaimController {

    private final DamageClaimService claimService;

    @PostMapping("/{userId}/file")
    public DamageClaim fileClaim(@PathVariable Long userId, @RequestBody DamageClaim claim) {
        return claimService.fileClaim(userId, claim);
    }

    @GetMapping("/{id}")
    public DamageClaim getClaim(@PathVariable Long id) {
        return claimService.getClaim(id);
    }

    @GetMapping("/{id}/evaluate")
    public String evaluateClaim(@PathVariable Long id) {
        return claimService.evaluateClaim(id);
    }
}
