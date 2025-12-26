package com.example.demo.model;

public class ClaimRule {
    private Long id;
    private String ruleName;
    private String description;
    private Double weight;

    public ClaimRule() {}

    public ClaimRule(String ruleName, String description, Double weight) {
        this.ruleName = ruleName;
        this.description = description;
        this.weight = weight;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRuleName() { return ruleName; }
    public void setRuleName(String ruleName) { this.ruleName = ruleName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }
}