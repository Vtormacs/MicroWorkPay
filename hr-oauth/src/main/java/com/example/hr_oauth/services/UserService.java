package com.example.hr_oauth.services;

import com.example.hr_oauth.entities.User;
import com.example.hr_oauth.feignclients.UserFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserFeignClient userFeignClient;

    public User findByEmail(String email) {
        try {
        User user = userFeignClient.getUserForEmail(email).getBody();
        if (user == null) {
            logger.error("Email not found: " + email);
            throw new IllegalArgumentException("Email not found");
        }
        logger.error("Email found: " + email);
        return user;
        } catch (Exception e) {
            throw new IllegalArgumentException("Email not found", e);
        }
    }
}
