package com.example.register.controllers;


import com.example.register.models.CustomUserDetails;
import com.example.register.models.LoginRequest;
import com.example.register.models.RegistrationRequest;
import com.example.register.services.ConfirmationTokenService;
import com.example.register.services.CustomUserDetailsService;
import com.example.register.services.RegistrationService;
import com.example.register.models.User;
import com.example.register.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@Controller
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("")//http://localhost:8080
@AllArgsConstructor
public class AppController {
    @Autowired
    private UserRepository userRepo;
    private RegistrationService registrationService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping("/register")
    public String register(@RequestBody
                           RegistrationRequest request) {
        return registrationService.register(request);
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        // Check if user exists in the database with the given email
        User user = customUserDetailsService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
        // Verify the provided password against the hashed password in the database
        boolean isPasswordMatched = BCrypt.checkpw(password, user.getPassword());
        if (!isPasswordMatched) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
        // If user exists and password matches, return a success response
        return ResponseEntity.ok("Login successful");
    }



}


