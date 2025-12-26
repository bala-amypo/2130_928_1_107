package com.example.demo.model;

public class User {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String role; 

    public User() {}

    public User(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    // CRITICAL FIX: Default to "AGENT" if null
    public String getRole() { 
        return (role == null) ? "AGENT" : role; 
    }
    public void setRole(String role) { this.role = role; }
}