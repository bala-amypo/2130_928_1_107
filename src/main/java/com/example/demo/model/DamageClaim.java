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

    @ManyToOne
    private Parcel parcel;

    private String claimDescription;

    private LocalDateTime filedAt;

    private String status = "PENDING";

    private Double score = 0.0;

    @ManyToMany
    private Set<ClaimRule> appliedRules = new HashSet<>();

    @OneToMany(mappedBy = "claim")
    private Set<Evidence> evidence = new HashSet<>();

    public DamageClaim() {}

    @PrePersist
    public void onCreate() {
        this.filedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    // REQUIRED BY TESTS
    public void setId(Long id) {
        this.id = id;
    }

    // getters & setters for other fields...
}
