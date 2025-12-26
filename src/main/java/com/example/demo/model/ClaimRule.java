package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class ClaimRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String keyword;   // ✅ REQUIRED FOR RULE ENGINE

    private double weight;

    // ---------- GETTERS & SETTERS ----------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeyword() {     // ✅ REQUIRED
        return keyword;
    }

    public void setKeyword(String keyword) { // ✅ REQUIRED
        this.keyword = keyword;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
