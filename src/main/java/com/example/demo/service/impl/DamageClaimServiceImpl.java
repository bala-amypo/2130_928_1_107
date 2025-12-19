package com.example.demo.service.impl;

import com.example.demo.model.DamageClaim;
import com.example.demo.model.User;
import com.example.demo.repository.DamageClaimRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.DamageClaimService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DamageClaimServiceImpl implements DamageClaimService {

    private final DamageClaimRepository claimRepo;
    private final UserRepository userRepo;

    @Override
    public DamageClaim fileClaim(Long userId, DamageClaim claim) {
        User user = userRepo.findById(userId).orElseThrow();
        claim.setUser(user);
        claim.setStatus("PENDING");
        return claimRepo.save(claim);
    }

    @Override
    public DamageClaim getClaim(Long id) {
        return claimRepo.findById(id).orElseThrow();
    }

    @Override
    public String evaluateClaim(Long id) {
        DamageClaim claim = getClaim(id);
        claim.setStatus("APPROVED"); // Simple placeholder logic
        claimRepo.save(claim);
        return claim.getStatus();
    }
}
