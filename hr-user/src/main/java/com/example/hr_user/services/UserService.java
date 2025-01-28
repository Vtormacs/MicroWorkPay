package com.example.hr_user.services;


import com.example.hr_user.entities.User;
import com.example.hr_user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findById(UUID id) {
        User user = userRepository.findById(id).orElseThrow();
        return user;
    }

    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user;
    }
}
