package com.example.demo.repository;

import com.example.demo.model.DamageClaim;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DamageClaimRepository extends JpaRepository<DamageClaim, Long> {}
