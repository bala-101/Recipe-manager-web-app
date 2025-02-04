package com.example.recipe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.recipe.model.User;
import com.example.recipe.model.ApiResponse; // Import the ApiResponse class
import com.example.recipe.service.AuthService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        if (username == null || password == null) {
            return ResponseEntity.badRequest().body(new ApiResponse("Username and password must be provided", null));
        }

        boolean isValid = authService.validateUser(username, password);
        
        if (isValid) {
            // Optionally include a token or session ID here
            return ResponseEntity.ok(new ApiResponse("Login successful", "http://localhost:3000/dashboard"));
        }
        
        return ResponseEntity.badRequest().body(new ApiResponse("Invalid credentials", null));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody User user) {
        try {
            authService.registerUser(user);
            return ResponseEntity.ok(new ApiResponse("Registration successful", null));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse("Registration failed: " + e.getMessage(), null));
        }
    }
}