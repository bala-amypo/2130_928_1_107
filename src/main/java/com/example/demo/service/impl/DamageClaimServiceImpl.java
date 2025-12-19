package com.example.demo.service.impl;

import com.example.demo.model.DamageClaim;
import com.example.demo.model.User;
import com.example.demo.repository.DamageClaimRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DamageClaimServiceImpl {

    private final DamageClaimRepository damageClaimRepository;
    private final UserRepository userRepository;

    public DamageClaimServiceImpl(DamageClaimRepository damageClaimRepository,
                                  UserRepository userRepository) {
        this.damageClaimRepository = damageClaimRepository;
        this.userRepository = userRepository;
    }

    public DamageClaim createClaim(Long userId, DamageClaim claim) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        claim.setUser(user);
        return damageClaimRepository.save(claim);
    }

    public List<DamageClaim> getClaimsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getClaims();
    }
}
