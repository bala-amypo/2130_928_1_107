package com.example.demo.controller;

import com.example.demo.model.DamageClaim;
import com.example.demo.service.DamageClaimService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/claims")
public class DamageClaimController {

    private final DamageClaimService damageClaimService;

    public DamageClaimController(DamageClaimService damageClaimService) {
        this.damageClaimService = damageClaimService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<DamageClaim> createClaim(@PathVariable Long userId,
                                                   @RequestBody DamageClaim claim) {
        DamageClaim createdClaim = damageClaimService.createClaim(userId, claim);
        return ResponseEntity.ok(createdClaim);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<DamageClaim>> getUserClaims(@PathVariable Long userId) {
        List<DamageClaim> claims = damageClaimService.getClaimsByUser(userId);
        return ResponseEntity.ok(claims);
    }
}
