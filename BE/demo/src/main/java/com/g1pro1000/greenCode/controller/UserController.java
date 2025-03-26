package com.g1pro1000.greenCode.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;


import com.g1pro1000.greenCode.model.ForumPost;
import com.g1pro1000.greenCode.model.User;
import com.g1pro1000.greenCode.repository.ForumCommentRepository;
import com.g1pro1000.greenCode.repository.ForumPostRepository;
import com.g1pro1000.greenCode.repository.UserRepository;
import com.g1pro1000.greenCode.repository.UserScoreRepository;
import com.g1pro1000.greenCode.service.UserService;

import java.util.Optional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired private UserService userService;
    @Autowired private ForumCommentRepository forumCommentRepository;
    @Autowired private ForumPostRepository forumPostRepository;
    @Autowired private UserScoreRepository userScoreRepository;
    @Autowired private UserRepository userRepository;

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
        response.put("email", user.getEmail());
        response.put("phone", user.getPhone());
        response.put("loggedIn", true);
        return ResponseEntity.ok(response);
    }
    @Transactional
    @DeleteMapping("/delete")
public ResponseEntity<String> deleteUser(HttpSession session) {
    User user = (User) session.getAttribute("user");
    if (user == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Ingen bruker innlogget");
    }

    try {
        // 1. Slett kommentarer på brukerens foruminnlegg
        List<ForumPost> posts = forumPostRepository.findByAuthor(user.getUsername());
        for (ForumPost post : posts) {
            forumCommentRepository.deleteByPostId(post.getId());
            System.out.println("🔹 Sletter kommentarer til brukerens foruminnlegg");

        }

        // 2. Slett brukerens egne kommentarer
        forumCommentRepository.deleteByAuthor(user.getUsername());
        System.out.println("🔹 Sletter egne kommentarer");


        // 3. Slett brukerens foruminnlegg
        forumPostRepository.deleteByAuthor(user.getUsername());
        System.out.println("🔹 Sletter foruminnlegg");


        // 4. Slett poeng (manuelt hent objekt og slett det)
        userScoreRepository.findByUserId(user.getId()).ifPresent(score -> {
            userScoreRepository.delete(score);
        });

        // 5. Slett brukeren
        userRepository.deleteById(user.getId());
        System.out.println("🔹 Sletter bruker");


        // 6. Avslutt session
        session.invalidate();
        return ResponseEntity.ok("Bruker og all data er slettet");

    } catch (Exception e) {
        e.printStackTrace(); // For feilsøking i terminal
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Feil ved sletting: " + e.getMessage());
    }
}

    
}
