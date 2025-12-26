package com.example.demo.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class DamageClaim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tests expect THIS name
    private String claimDescription;

    private Double score;
    private String status;

    @ManyToOne
    private Parcel parcel;

    @ManyToMany
    private Set<ClaimRule> appliedRules = new HashSet<>();

    // ---------- REQUIRED BY TESTS ----------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // 👇 REQUIRED
    public String getClaimDescription() {
        return claimDescription;
    }

    // 👇 REQUIRED
    public void setClaimDescription(String claimDescription) {
        this.claimDescription = claimDescription;
    }

    // 👇 Used by RuleEngine internally
    public String getDescription() {
        return claimDescription;
    }

    public void setDescription(String description) {
        this.claimDescription = description;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
