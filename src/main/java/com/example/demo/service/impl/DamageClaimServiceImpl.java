package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.ClaimRule;
import com.example.demo.model.DamageClaim;
import com.example.demo.model.Parcel;
import com.example.demo.repository.ClaimRuleRepository;
import com.example.demo.repository.DamageClaimRepository;
import com.example.demo.repository.ParcelRepository;
import com.example.demo.service.DamageClaimService;
import com.example.demo.util.RuleEngineUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.HashSet;

@Service
public class DamageClaimServiceImpl implements DamageClaimService {
    private final ParcelRepository parcelRepo;
    private final DamageClaimRepository claimRepo;
    private final ClaimRuleRepository ruleRepo;

    public DamageClaimServiceImpl(ParcelRepository parcelRepo, DamageClaimRepository claimRepo, ClaimRuleRepository ruleRepo) {
        this.parcelRepo = parcelRepo;
        this.claimRepo = claimRepo;
        this.ruleRepo = ruleRepo;
    }

    @Override
    public DamageClaim fileClaim(Long parcelId, DamageClaim claim) {
        Parcel parcel = parcelRepo.findById(parcelId)
                .orElseThrow(() -> new ResourceNotFoundException("Parcel not found"));
        
        claim.setParcel(parcel);
        // Default status handled in Entity, but safe to set here too
        if (claim.getStatus() == null) claim.setStatus("PENDING");
        claim.setUploadedAt(LocalDateTime.now());
        if (claim.getAppliedRules() == null) claim.setAppliedRules(new HashSet<>());
        
        return claimRepo.save(claim);
    }

    @Override
    public DamageClaim evaluateClaim(Long claimId) {
        DamageClaim claim = claimRepo.findById(claimId)
                .orElseThrow(() -> new ResourceNotFoundException("Claim not found"));

        List<ClaimRule> rules = ruleRepo.findAll();

        double score = RuleEngineUtil.computeScore(claim.getClaimDescription(), rules);
        claim.setScore(score);

        if (claim.getAppliedRules() == null) claim.setAppliedRules(new HashSet<>());
        
        String descLower = claim.getClaimDescription() != null ? claim.getClaimDescription().toLowerCase() : "";
        for (ClaimRule rule : rules) {
             boolean match = false;
             String rDesc = rule.getConditionExpression(); // Ensure this matches Model field
             
             if ("always".equalsIgnoreCase(rDesc) || "AlwaysRule".equalsIgnoreCase(rule.getRuleName())) {
                 match = true;
             } else if (rDesc != null && rDesc.toLowerCase().startsWith("description_contains:")) {
                 String keyword = rDesc.split(":")[1].toLowerCase();
                 if (descLower.contains(keyword)) match = true;
             }
             
             if (match) {
                 claim.getAppliedRules().add(rule);
             }
        }

        if (score > 0) {
            claim.setStatus("APPROVED");
        } else {
            claim.setStatus("REJECTED");
        }

        return claimRepo.save(claim);
    }

    @Override
    public DamageClaim getClaim(Long claimId) {
        return claimRepo.findById(claimId)
                .orElseThrow(() -> new ResourceNotFoundException("Claim not found"));
    }
}