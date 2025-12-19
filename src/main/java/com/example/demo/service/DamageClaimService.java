package com.example.demo.service;

import com.example.demo.model.DamageClaim;

public interface DamageClaimService {
    DamageClaim fileClaim(Long userId, DamageClaim claim);
    DamageClaim getClaim(Long id);
    String evaluateClaim(Long id);
}
