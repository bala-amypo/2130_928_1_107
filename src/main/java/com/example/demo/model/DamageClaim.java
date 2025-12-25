package com.example.demo.model;

import jakarta.persistence.*;
import java.util.*;

@Entity
public class DamageClaim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String claimDescription;
    private String status;
    private Double score;

    @ManyToOne
    private Parcel parcel;

    @ManyToMany
    private Set<ClaimRule> appliedRules = new HashSet<>();

    // ✅ FIX: constructor defaults (tests depend on this)
    public DamageClaim() {
        this.status = "PENDING";
        this.score = null;
    }

    // ===== GETTERS & SETTERS =====

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getClaimDescription() { return claimDescription; }
    public void setClaimDescription(String claimDescription) {
        this.claimDescription = claimDescription;
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }

    public Parcel getParcel() { return parcel; }
    public void setParcel(Parcel parcel) { this.parcel = parcel; }

    public Set<ClaimRule> getAppliedRules() { return appliedRules; }
}
