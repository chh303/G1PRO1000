package com.g1pro1000.greenCode.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.g1pro1000.greenCode.model.User;
import com.g1pro1000.greenCode.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {


    @Autowired
    private final UserService userService;

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
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
