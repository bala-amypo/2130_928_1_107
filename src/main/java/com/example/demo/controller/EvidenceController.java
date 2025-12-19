package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.model.Evidence;
import com.example.demo.service.EvidenceService;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/evidence")
@Tag(name = "Evidence")
public class EvidenceController {

    private final EvidenceService evidenceService;

    public EvidenceController(EvidenceService evidenceService) {
        this.evidenceService = evidenceService;
    }

    @PostMapping("/upload/{claimId}")
    @Operation(summary = "Upload evidence for a claim")
    public ApiResponse uploadEvidence(@PathVariable Long claimId,
                                      @RequestBody Evidence evidence) {
        Evidence saved = evidenceService.uploadEvidence(claimId, evidence);
        return new ApiResponse(true, "Evidence uploaded", saved);
    }

    @GetMapping("/claim/{claimId}")
    @Operation(summary = "Get evidence for a claim")
    public ApiResponse getEvidence(@PathVariable Long claimId) {
        List<Evidence> list = evidenceService.getEvidenceForClaim(claimId);
        return new ApiResponse(true, "Evidence list", list);
    }
}
