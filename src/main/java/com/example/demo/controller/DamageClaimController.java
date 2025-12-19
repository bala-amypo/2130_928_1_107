package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.model.DamageClaim;
import com.example.demo.service.DamageClaimService;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/claims")
@Tag(name = "Damage Claims")
public class DamageClaimController {

    private final DamageClaimService claimService;

    public DamageClaimController(DamageClaimService claimService) {
        this.claimService = claimService;
    }

    @PostMapping("/file/{parcelId}")
    @Operation(summary = "File a damage claim")
    public ApiResponse fileClaim(@PathVariable Long parcelId,
                                 @RequestBody DamageClaim claim) {
        DamageClaim saved = claimService.fileClaim(parcelId, claim);
        return new ApiResponse(true, "Claim filed", saved);
    }

    @PutMapping("/evaluate/{claimId}")
    @Operation(summary = "Evaluate a claim")
    public ApiResponse evaluateClaim(@PathVariable Long claimId) {
        DamageClaim evaluated = claimService.evaluateClaim(claimId);
        return new ApiResponse(true, "Claim evaluated", evaluated);
    }

    @GetMapping("/{claimId}")
    @Operation(summary = "Get claim by ID")
    public ApiResponse getClaim(@PathVariable Long claimId) {
        DamageClaim claim = claimService.getClaim(claimId);
        return new ApiResponse(true, "Claim found", claim);
    }
}
