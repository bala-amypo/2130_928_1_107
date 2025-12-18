package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class ClaimRule {

    @Id @GeneratedValue
    private Long id;

    private String keyword;
    private int weight;

    // getters & setters
}
