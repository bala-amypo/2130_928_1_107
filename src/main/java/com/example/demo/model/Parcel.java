package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "trackingNumber"))
public class Parcel {

    @Id @GeneratedValue
    private Long id;

    private String trackingNumber;
    private String sender;
    private String receiver;

    // getters & setters
}
