package com.realprojects.urlshortener.domain.services;

import com.realprojects.urlshortener.domain.entities.User;
import com.realprojects.urlshortener.domain.models.CreateUserCmd;
import com.realprojects.urlshortener.domain.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void createUser(CreateUserCmd cmd) {
        if (userRepository.existsByEmail(cmd.email())) {
            throw new RuntimeException("Email already exists");
        }
        var user = new User();
        user.setEmail(cmd.email());
        user.setPassword(passwordEncoder.encode(cmd.password()));//accepting password as plain text but getting it encoded
        user.setName(cmd.name());
        user.setRole(cmd.role());
        user.setCreatedAt(Instant.now());
        userRepository.save(user);
    }
}