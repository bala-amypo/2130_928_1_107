package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "claim_rules")
public class ClaimRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ruleName;
    
    // CHANGED: From 'keyword' to 'conditionExpression' per requirements
    private String conditionExpression; 
    
    private double weight;

    public ClaimRule() {
    }

    public ClaimRule(String ruleName, String conditionExpression, double weight) {
        this.ruleName = ruleName;
        this.conditionExpression = conditionExpression;
        this.weight = weight;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRuleName() { return ruleName; }
    public void setRuleName(String ruleName) { this.ruleName = ruleName; }

    public String getConditionExpression() { return conditionExpression; }
    public void setConditionExpression(String conditionExpression) { this.conditionExpression = conditionExpression; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }
}