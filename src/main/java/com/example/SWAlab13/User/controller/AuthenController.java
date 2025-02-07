package com.example.SWAlab13.User.controller;

import org.springframework.web.bind.annotation.*;
import com.example.SWAlab13.User.model.User;
import com.example.SWAlab13.User.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/login")
public class AuthenController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        // Manual validation
        Map<String, String> errors = new HashMap<>();

        // Validate username
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            errors.put("username", "Username is required");
        }

        // Validate email
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            errors.put("email", "Email is required");
        } else if (!isValidEmail(user.getEmail())) {
            errors.put("email", "Invalid email format");
        }

        // Validate password
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            errors.put("password", "Password is required");
        } else if (!isValidPassword(user.getPassword())) {
            errors.put("password", "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one number, and one special character");
        }

        // Return validation errors if any
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }

        // Check if the user already exists
        if (userService.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email already in use");
        }

        // Encode the password and save the user
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.createUser(user);

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user, HttpServletRequest request) {
        // Validate email and password
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body("Email is required");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body("Password is required");
        }

        // Authenticate the user
        User foundUser = userService.authenticate(user.getEmail(), user.getPassword());
        if (foundUser != null) {
            // Set user in session
            request.getSession().setAttribute("user", foundUser);
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        // Invalidate the session
        request.getSession().invalidate();
        return "Logged out successfully!";
    }

    // Helper method to validate email format
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }

    // Helper method to validate password
    private boolean isValidPassword(String password) {
        // Password must be at least 8 characters long
        if (password.length() < 8) {
            return false;
        }

        // Password must contain at least one uppercase letter
        if (!Pattern.compile(".*[A-Z].*").matcher(password).matches()) {
            return false;
        }

        // Password must contain at least one lowercase letter
        if (!Pattern.compile(".*[a-z].*").matcher(password).matches()) {
            return false;
        }

        // Password must contain at least one number
        if (!Pattern.compile(".*\\d.*").matcher(password).matches()) {
            return false;
        }

        // Password must contain at least one special character
        if (!Pattern.compile(".*[!@#$%^&*()_+=<>?/].*").matcher(password).matches()) {
            return false;
        }

        return true;
    }
}