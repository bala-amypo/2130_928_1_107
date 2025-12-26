package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class ClaimRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tests expect ruleName
    private String ruleName;

    private String keyword;
    private double weight;

    // ✅ REQUIRED NO-ARG CONSTRUCTOR
    public ClaimRule() {}

    // ✅ REQUIRED 3-ARG CONSTRUCTOR
    public ClaimRule(String ruleName, String keyword, double weight) {
        this.ruleName = ruleName;
        this.keyword = keyword;
        this.weight = weight;
    }

    // ---------- REQUIRED BY TESTS ----------

    public Long getId() {
        return id;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
