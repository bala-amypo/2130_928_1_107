package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class DamageClaim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

    private Double score;

    private String description;

    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    private Parcel parcel;

    @ManyToMany
    @JoinTable(
            name = "claim_rules_map",
            joinColumns = @JoinColumn(name = "claim_id"),
            inverseJoinColumns = @JoinColumn(name = "rule_id")
    )
    private Set<ClaimRule> appliedRules = new HashSet<>();

    // ---------------- REQUIRED BY TESTS ----------------

    // 🔑 Alias setter expected by tests
    public void setClaimDescription(String description) {
        this.description = description;
    }

    // 🔑 Alias getter expected by tests
    public String getClaimDescription() {
        return this.description;
    }

    // ---------------- NORMAL GETTERS / SETTERS ----------------

    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public Double getScore() {
        return score;
    }

    public Parcel getParcel() {
        return parcel;
    }

    public Set<ClaimRule> getAppliedRules() {
        return appliedRules;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public void setParcel(Parcel parcel) {
        this.parcel = parcel;
    }

    public void setAppliedRules(Set<ClaimRule> appliedRules) {
        this.appliedRules = appliedRules;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
