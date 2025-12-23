package com.example.demo.service.impl;

import com.example.demo.model.DamageClaim;
import com.example.demo.model.User;
import com.example.demo.repository.DamageClaimRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.DamageClaimService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DamageClaimServiceImpl implements DamageClaimService {

    private final DamageClaimRepository claimRepository;
    private final UserRepository userRepository;

    public DamageClaimServiceImpl(DamageClaimRepository claimRepository,
                                 UserRepository userRepository) {
        this.claimRepository = claimRepository;
        this.userRepository = userRepository;
    }

    @Override
    public DamageClaim createClaim(Long userId, DamageClaim claim) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        claim.setUser(user);               // 🔥 VERY IMPORTANT
        return claimRepository.save(claim);
    }

    @Override
    public List<DamageClaim> getClaimsByUser(Long userId) {
        return claimRepository.findByUserId(userId);
    }
}
