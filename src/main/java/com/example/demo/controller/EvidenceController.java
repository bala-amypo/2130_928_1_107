package com.example.demo.controller;

import com.example.demo.model.Evidence;
import com.example.demo.service.EvidenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/evidence")
@RequiredArgsConstructor
public class EvidenceController {

    private final EvidenceService evidenceService;

    @PostMapping("/{claimId}/upload")
    public Evidence uploadEvidence(@PathVariable Long claimId, @RequestBody Evidence evidence) {
        return evidenceService.uploadEvidence(claimId, evidence);
    }

    @GetMapping("/{claimId}")
    public List<Evidence> getEvidence(@PathVariable Long claimId) {
        return evidenceService.getEvidenceForClaim(claimId);
    }
}
