package com.example.demo.service;

import com.example.demo.model.DamageClaim;
import java.util.List;

public interface DamageClaimService {
    List<DamageClaim> getAllClaims();
    DamageClaim saveClaim(DamageClaim claim);
}
