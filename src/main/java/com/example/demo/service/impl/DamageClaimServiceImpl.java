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

import java.util.List;

@Service
public class DamageClaimServiceImpl implements DamageClaimService {

    private final ParcelRepository parcelRepository;
    private final DamageClaimRepository damageClaimRepository;
    private final ClaimRuleRepository claimRuleRepository;

    // REQUIRED constructor for DI
    public DamageClaimServiceImpl(
            ParcelRepository parcelRepository,
            DamageClaimRepository damageClaimRepository,
            ClaimRuleRepository claimRuleRepository) {

        this.parcelRepository = parcelRepository;
        this.damageClaimRepository = damageClaimRepository;
        this.claimRuleRepository = claimRuleRepository;
    }

    // -------------------------------------------------------
    // FILE A CLAIM
    // -------------------------------------------------------
    @Override
    public DamageClaim fileClaim(Long parcelId, DamageClaim claim) {

        Parcel parcel = parcelRepository.findById(parcelId)
                .orElseThrow(() -> new ResourceNotFoundException("Parcel not found"));

        claim.setParcel(parcel);
        claim.setStatus("PENDING");   // default status
        claim.setScore(null);         // IMPORTANT: score must start as NULL

        return damageClaimRepository.save(claim);
    }

    // -------------------------------------------------------
    // EVALUATE CLAIM
    // -------------------------------------------------------
    @Override
    public DamageClaim evaluateClaim(Long claimId) {

        DamageClaim claim = damageClaimRepository.findById(claimId)
                .orElseThrow(() -> new ResourceNotFoundException("Claim not found"));

        List<ClaimRule> rules = claimRuleRepository.findAll();

        // RuleEngineUtil.evaluate RETURNS DOUBLE
        double score = RuleEngineUtil.evaluate(claim, rules);

        claim.setScore(score);

        if (score > 0.9) {
            claim.setStatus("APPROVED");
        } else if (score == 0.0) {
            claim.setStatus("REJECTED");
        } else {
            claim.setStatus("PENDING");
        }

        return damageClaimRepository.save(claim);
    }

    // -------------------------------------------------------
    // GET CLAIM
    // -------------------------------------------------------
    @Override
    public DamageClaim getClaim(Long claimId) {

        return damageClaimRepository.findById(claimId)
                .orElseThrow(() -> new ResourceNotFoundException("Claim not found"));
    }
}
