package com.g1pro1000.greenCode.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.g1pro1000.greenCode.model.UserDetails;
import com.g1pro1000.greenCode.service.UserDetailsService;

/**
 *
 * @author ch.h
 */

@RestController
@RequestMapping("/api/userdetails")
public class UserDetailsController {

    @Autowired
    private UserDetailsService userDetailsService;

    @PutMapping("/update-score/{userId}")
    public ResponseEntity<String> updateScore(@PathVariable Long userId) {
        boolean success = userDetailsService.increaseScore(userId, 50);
        if (success) {
            return ResponseEntity.ok("Score oppdatert");
        } else {
            return ResponseEntity.badRequest().body("Bruker ikke funnet");
        }
    }

    @PostMapping("/save")
    public ResponseEntity<UserDetails> saveUserDetails(@RequestBody UserDetails userDetails) {
        return ResponseEntity.ok(userDetailsService.saveUserDetails(userDetails));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDetails> getUserDetails(@PathVariable Long userId) {
        Optional<UserDetails> details = userDetailsService.getUserDetailsByUserId(userId);
        return details.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}