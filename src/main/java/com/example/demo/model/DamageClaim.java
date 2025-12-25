package com.example.demo.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    // ================= CONSTRUCTOR =================
    public DamageClaim() {
        this.status = "PENDING";     // default required by tests
        this.score = null;           // score must start null
    }

    // ================= GETTERS & SETTERS =================

    public Long getId() {
        return id;
    }

    // 🔥 THIS WAS MISSING (TESTS REQUIRE IT)
    public void setId(Long id) {
        this.id = id;
    }

    public String getClaimDescription() {
        return claimDescription;
    }

    public void setClaimDescription(String claimDescription) {
        this.claimDescription = claimDescription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Parcel getParcel() {
        return parcel;
    }

    public void setParcel(Parcel parcel) {
        this.parcel = parcel;
    }

    public Set<ClaimRule> getAppliedRules() {
        return appliedRules;
    }

    public void setAppliedRules(Set<ClaimRule> appliedRules) {
        this.appliedRules = appliedRules;
    }
}
