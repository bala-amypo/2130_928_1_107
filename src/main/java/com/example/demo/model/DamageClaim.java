package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "damage_claims")
public class DamageClaim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Parcel parcel;

    private String claimDescription;

    private LocalDateTime filedAt;

    private String status = "PENDING";

    private Double score;

    @ManyToMany
    @JoinTable(
        name = "claim_rule_map",
        joinColumns = @JoinColumn(name = "claim_id"),
        inverseJoinColumns = @JoinColumn(name = "rule_id")
    )
    private Set<ClaimRule> appliedRules = new HashSet<>();

    @OneToMany(mappedBy = "claim", cascade = CascadeType.ALL)
    private List<Evidence> evidenceList = new ArrayList<>();

    public DamageClaim() {}

    @PrePersist
    void onCreate() {
        filedAt = LocalDateTime.now();
    }

    // getters & setters
}
