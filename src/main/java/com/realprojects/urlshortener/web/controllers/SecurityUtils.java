package com.realprojects.urlshortener.web.controllers;

import com.realprojects.urlshortener.domain.entities.User;
import com.realprojects.urlshortener.domain.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityUtils {

    private final UserRepository userRepository;

    public SecurityUtils(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            return userRepository.findByEmail(email).orElse(null);
        }
        return null;
    }

    public Long getCurrentUserId() { // this method will return a userID incase of a logged in user
        User user = getCurrentUser();
        return user != null ? user.getId() : null;
    }
}
