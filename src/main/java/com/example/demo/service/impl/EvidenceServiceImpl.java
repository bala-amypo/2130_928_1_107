package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.DamageClaim;
import com.example.demo.model.Evidence;
import com.example.demo.repository.DamageClaimRepository;
import com.example.demo.repository.EvidenceRepository;
import com.example.demo.service.EvidenceService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EvidenceServiceImpl implements EvidenceService {
    private final EvidenceRepository evidenceRepo;
    private final DamageClaimRepository claimRepo;

    public EvidenceServiceImpl(EvidenceRepository evidenceRepo, DamageClaimRepository claimRepo) {
        this.evidenceRepo = evidenceRepo;
        this.claimRepo = claimRepo;
    }

    @Override
    public Evidence uploadEvidence(Long claimId, Evidence evidence) {
        DamageClaim claim = claimRepo.findById(claimId)
                .orElseThrow(() -> new ResourceNotFoundException("Claim not found"));
        
        evidence.setClaim(claim);
        evidence.setUploadedAt(LocalDateTime.now());
        
        return evidenceRepo.save(evidence);
    }

    @Override
    public List<Evidence> getEvidenceForClaim(Long claimId) {
        return evidenceRepo.findByClaim_Id(claimId);
    }
}