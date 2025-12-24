package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "damage_claim")
public class DamageClaim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Parcel parcel;

    private String description;

    // ✅ MUST BE NULL INITIALLY
    private Double score;

    private String status;

    @ManyToMany
    private Set<ClaimRule> appliedRules = new HashSet<>();

    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = "PENDING";
        }
        // ❌ DO NOT initialize score here
    }

    // getters & setters
}
