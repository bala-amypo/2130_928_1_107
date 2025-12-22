package com.example.demo.service;

import com.example.demo.model.DamageClaim;
import java.util.List;

public interface DamageClaimService {
    DamageClaim createClaim(Long userId, DamageClaim claim);
    List<DamageClaim> getClaimsByUser(Long userId);
}
