package com.example.demo.controller;

import com.example.demo.model.Evidence;
import com.example.demo.service.EvidenceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/evidence")
public class EvidenceController {

    private final EvidenceService evidenceService;

    public EvidenceController(EvidenceService evidenceService) {
        this.evidenceService = evidenceService;
    }

    @PostMapping("/upload/{claimId}")
    public Evidence upload(
            @PathVariable Long claimId,
            @RequestBody Evidence evidence) {

        return evidenceService.uploadEvidence(claimId, evidence);
    }

    @GetMapping("/claim/{claimId}")
    public List<Evidence> getForClaim(@PathVariable Long claimId) {
        return evidenceService.getEvidenceForClaim(claimId);
    }
}
