package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class Evidence {

    @Id @GeneratedValue
    private Long id;

    private String type;
    private String content;

    @ManyToOne
    private DamageClaim claim;

    // getters & setters
}
