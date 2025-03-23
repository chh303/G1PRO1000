package com.g1pro1000.greenCode.controller;

import java.util.HashMap;
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

import jakarta.servlet.http.HttpSession;

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
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody User user, HttpSession session) {
        Optional<User> foundUser = userService.loginUser(user.getUsername(), user.getPassword());
        Map<String, Object> response = new HashMap<>();
    
        if (foundUser.isPresent()) {
            session.setAttribute("user", foundUser.get()); 
            session.setAttribute("userId", foundUser.get().getId()); 
            session.setAttribute("username", foundUser.get().getUsername()); 
            
            System.out.println("✅ Bruker logget inn - Session ID: " + session.getId());
            response.put("message", "Innlogging vellykket");
            response.put("userId", foundUser.get().getId());
            response.put("username", foundUser.get().getUsername());
            response.put("loggedIn", true);
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Feil brukernavn eller passord");
            return ResponseEntity.status(401).body(response);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Bruker logget ut");
    }

    @GetMapping("/session")
    public ResponseEntity<Map<String, Object>> getSessionUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
    
        if (user == null) {
            System.out.println("❌ Ingen aktiv session funnet.");
            return ResponseEntity.ok(Map.of("loggedIn", false));
        }
    
        System.out.println("✅ Aktiv session funnet for bruker: " + user.getUsername());
    
        Map<String, Object> response = new HashMap<>();
        response.put("userId", user.getId());
        response.put("username", user.getUsername());
        response.put("loggedIn", true);
        return ResponseEntity.ok(response);
    }
}
