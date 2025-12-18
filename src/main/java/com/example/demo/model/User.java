package com.example.demo.model;
import jakarta.persistence.*;
@Entity
@Table(name="users",uniqueContraints=
@UniqueContraint(columnNames="email"))
public class User{
    @Id @GenerateValue
    private Long id;
    private STring name;
    private
}
