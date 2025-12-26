package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class ClaimRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ruleName;
    private String keyword;
    private double weight;

    public ClaimRule() {
    }

    public ClaimRule(String ruleName, String keyword, double weight) {
        this.ruleName = ruleName;
        this.keyword = keyword;
        this.weight = weight;
    }

    // ---------- getters & setters ----------

    public Long getId() {
        return id;
    }

    public String getRuleName() {
        return ruleName;
    }

    public String getKeyword() {
        return keyword;
    }

    public double getWeight() {
        return weight;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
