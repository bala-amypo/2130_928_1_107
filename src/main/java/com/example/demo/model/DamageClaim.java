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

    @ManyToOne(optional = false)
    private Parcel parcel;

    @Column(length = 1000)
    private String claimDescription;

    private LocalDateTime filedAt;

    private String status = "PENDING";

    private Double score;

    @ManyToMany
    @JoinTable(
        name = "claim_rule_mapping",
        joinColumns = @JoinColumn(name = "claim_id"),
        inverseJoinColumns = @JoinColumn(name = "rule_id")
    )
    private Set<ClaimRule> appliedRules = new HashSet<>();

    @OneToMany(mappedBy = "claim", cascade = CascadeType.ALL)
    private Set<Evidence> evidenceSet = new HashSet<>();

    public DamageClaim() {
        this.status = "PENDING";
    }

    @PrePersist
    public void prePersist() {
        this.filedAt = LocalDateTime.now();
    }

    // getters & setters

    public Long getId() {
        return id;
    }

    public Parcel getParcel() {
        return parcel;
    }

    public void setParcel(Parcel parcel) {
        this.parcel = parcel;
    }

    public String getClaimDescription() {
        return claimDescription;
    }

    public void setClaimDescription(String claimDescription) {
        this.claimDescription = claimDescription;
    }

    public LocalDateTime getFiledAt() {
        return filedAt;
    }

    public String getStatus() {
        return status;
    }

    public Double getScore() {
        return score;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Set<ClaimRule> getAppliedRules() {
        return appliedRules;
    }
}
