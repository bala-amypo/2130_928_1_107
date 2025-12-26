package com.example.demo.service.impl;

import com.example.demo.model.DamageClaim;
import com.example.demo.model.Evidence;
import com.example.demo.repository.DamageClaimRepository;
import com.example.demo.repository.EvidenceRepository;
import com.example.demo.service.EvidenceService;
import java.util.Optional;

public class EvidenceServiceImpl implements EvidenceService {
    private EvidenceRepository evidenceRepo;
    private DamageClaimRepository claimRepo;

    public EvidenceServiceImpl(EvidenceRepository evidenceRepo, DamageClaimRepository claimRepo) {
        this.evidenceRepo = evidenceRepo;
        this.claimRepo = claimRepo;
    }

    @Override
    public Evidence uploadEvidence(Long claimId, Evidence evidence) throws Exception {
        Optional<DamageClaim> claimOpt = claimRepo.findById(claimId);
        if (!claimOpt.isPresent()) {
            throw new Exception("Claim not found");
        }
        evidence.setClaim(claimOpt.get());
        return evidenceRepo.save(evidence);
    }
    
    // getEvidenceForClaim implementation...
}