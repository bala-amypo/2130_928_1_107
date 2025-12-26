package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "damage_claims")
public class DamageClaim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FIX: Initialize default status to "PENDING" for the entity test case
    private String status = "PENDING";

    private Double score;

    private String description;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "parcel_id")
    private Parcel parcel;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "claim_rules_map",
            joinColumns = @JoinColumn(name = "claim_id"),
            inverseJoinColumns = @JoinColumn(name = "rule_id")
    )
    private Set<ClaimRule> appliedRules = new HashSet<>();

    // Auto-generate timestamp on save
    @PrePersist
    public void onPrePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        if (this.status == null) {
            this.status = "PENDING";
        }
    }

    // ---------------- REQUIRED BY TESTS ----------------
    public void setClaimDescription(String description) {
        this.description = description;
    }

    public String getClaimDescription() {
        return this.description;
    }

    // ---------------- GETTERS / SETTERS ----------------

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }

    public Parcel getParcel() { return parcel; }
    public void setParcel(Parcel parcel) { this.parcel = parcel; }

    public Set<ClaimRule> getAppliedRules() { return appliedRules; }
    public void setAppliedRules(Set<ClaimRule> appliedRules) { this.appliedRules = appliedRules; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}