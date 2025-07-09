package com.example.phonesuggester.dto;

public class AuthResponse {
    
    private String token;
    private String message;
    private String username;
    private String email;

    // Constructors
    public AuthResponse() {}

    public AuthResponse(String token, String message, String username, String email) {
        this.token = token;
        this.message = message;
        this.username = username;
        this.email = email;
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
} 