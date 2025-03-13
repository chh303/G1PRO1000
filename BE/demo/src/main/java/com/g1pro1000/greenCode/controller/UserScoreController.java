package com.g1pro1000.greenCode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.g1pro1000.greenCode.model.UserScore;
import com.g1pro1000.greenCode.service.UserScoreService;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/score")
public class UserScoreController {

    @Autowired
    private UserScoreService userScoreService;

    // 🔹 Henter brukerens nåværende score
    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUserScore(@PathVariable Long userId) {
        Optional<UserScore> userScore = userScoreService.getUserScore(userId);

        if (userScore.isPresent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("username", userScore.get().getUser().getUsername()); // 🔹 Henter brukernavn
            response.put("score", userScore.get().getScore());  // 🔹 Returnerer riktig score
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(404).body("Brukers score ikke funnet");
        }
    }

    // 🔹 Henter leaderboard med brukernavn og score
    @GetMapping("/leaderboard")
    public ResponseEntity<List<Map<String, Object>>> getLeaderboard() {
        List<UserScore> leaderboard = userScoreService.getLeaderboard();
        List<Map<String, Object>> response = leaderboard.stream().map(userScore -> {
            Map<String, Object> map = new HashMap<>();
            map.put("username", userScore.getUser().getUsername()); // 🔹 Henter brukernavn
            map.put("score", userScore.getScore()); // 🔹 Henter score
            return map;
        }).toList();
        return ResponseEntity.ok(response);
    }

    // 🔹 Oppdaterer brukerens score
    @PostMapping("/update")
    public ResponseEntity<String> updateScore(@RequestBody ScoreUpdateRequest request) {
        if (request == null || request.getUserId() == null) {
            return ResponseEntity.badRequest().body("Feil: Mangler userId eller request-data!");
        }

        // 🔹 Sjekk om brukeren finnes i databasen
        Optional<UserScore> userScore = userScoreService.getUserScore(request.getUserId());
        if (userScore.isEmpty()) {
            return ResponseEntity.status(404).body("Feil: Bruker ikke funnet!");
        }

        // 🔹 Oppdater score i databasen
        String response = userScoreService.updateScore(request.getUserId(), request.getScore(), request.getAchievements());
        return ResponseEntity.ok(response);
    }

    // 🔹 Intern klasse for å håndtere JSON-data fra frontend
    public static class ScoreUpdateRequest {
        private Long userId;
        private int score;
        private List<String> achievements;

        public Long getUserId() { return userId; }
        public int getScore() { return score; }
        public List<String> getAchievements() { return achievements; }
    }
}
