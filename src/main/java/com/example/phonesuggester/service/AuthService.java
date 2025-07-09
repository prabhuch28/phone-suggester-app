package com.example.phonesuggester.service;

import com.example.phonesuggester.dto.AuthRequest;
import com.example.phonesuggester.dto.AuthResponse;
import com.example.phonesuggester.model.User;
import com.example.phonesuggester.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthResponse register(AuthRequest request) {
        // Check if user already exists
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return new AuthResponse(null, "Username already exists", null, null);
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return new AuthResponse(null, "Email already exists", null, null);
        }

        // Create new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of("USER"));

        User savedUser = userRepository.save(user);

        return new AuthResponse(
            "dummy-token-" + savedUser.getId(), // Simple token for demo
            "User registered successfully",
            savedUser.getUsername(),
            savedUser.getEmail()
        );
    }

    public AuthResponse login(AuthRequest request) {
        Optional<User> userOpt = userRepository.findByUsername(request.getUsername());
        
        if (userOpt.isEmpty()) {
            return new AuthResponse(null, "Invalid username or password", null, null);
        }

        User user = userOpt.get();
        
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return new AuthResponse(null, "Invalid username or password", null, null);
        }

        return new AuthResponse(
            "dummy-token-" + user.getId(), // Simple token for demo
            "Login successful",
            user.getUsername(),
            user.getEmail()
        );
    }
} 