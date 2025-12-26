package com.example.demo.service.impl;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

public class UserServiceImpl implements UserService {
    private UserRepository userRepo;

    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User register(User user) throws Exception { // Bytecode expects exception on duplicate
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new Exception("Email cannot be empty");
        }
        if (userRepo.existsByEmail(user.getEmail())) {
            throw new Exception("email already exists"); 
        }
        return userRepo.save(user);
    }
    
    // Other methods...
}