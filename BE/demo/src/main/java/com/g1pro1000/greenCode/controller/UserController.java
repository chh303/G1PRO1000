package com.g1pro1000.greenCode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.g1pro1000.greenCode.model.User;
import com.g1pro1000.greenCode.service.UserService;

import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody User user) {
        String response = userService.registerUser(user);
        Map<String, String> responseBody = new HashMap<>();
        
        if (response.equals("Bruker registrert!")) {
            responseBody.put("message", response);
            return ResponseEntity.ok(responseBody);
        } else {
            responseBody.put("error", response);
            return ResponseEntity.badRequest().body(responseBody);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody User user) {
        Optional<User> foundUser = userService.loginUser(user.getUsername(), user.getPassword());
        Map<String, Object> response = new HashMap<>();

        if (foundUser.isPresent()) {
            response.put("message", "Innlogging vellykket");
            response.put("userId", foundUser.get().getId()); // Henter userId fra User-objektet
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Feil brukernavn eller passord");
            return ResponseEntity.status(401).body(response);
        }
    }
}
