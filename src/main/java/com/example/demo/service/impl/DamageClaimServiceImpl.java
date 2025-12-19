package com.example.demo.service.impl;

import com.example.demo.model.ClaimRule;
import com.example.demo.model.DamageClaim;
import com.example.demo.repository.ClaimRuleRepository;
import com.example.demo.repository.DamageClaimRepository;
import com.example.demo.service.DamageClaimService;
import com.example.demo.util.RuleEngineUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DamageClaimServiceImpl implements DamageClaimService {

    private final DamageClaimRepository claimRepository;
    private final ClaimRuleRepository ruleRepository;

    @Override
    public DamageClaim fileClaim(Long userId, DamageClaim claim) {
        claim.setUserId(userId);
        return claimRepository.save(claim);
    }

    @Override
    public DamageClaim getClaim(Long claimId) {
        return claimRepository.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found"));
    }

    @Override
    public String evaluateClaim(Long claimId) {
        DamageClaim claim = getClaim(claimId);
        List<ClaimRule> rules = ruleRepository.findAll();

        double score = RuleEngineUtil.evaluateRules(claim.getDescription(), rules);

        return score > 5 ? "Approved" : "Rejected";  // simple scoring threshold
    }
}
