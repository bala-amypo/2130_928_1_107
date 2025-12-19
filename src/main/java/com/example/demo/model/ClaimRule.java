package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ClaimRule {

    @Id
    private Long id;
    private String keyword;
    private int weight;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getKeyword() { return keyword; }
    public void setKeyword(String keyword) { this.keyword = keyword; }

    public int getWeight() { return weight; }
    public void setWeight(int weight) { this.weight = weight; }
}
