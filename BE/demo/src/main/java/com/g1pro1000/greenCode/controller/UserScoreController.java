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

@GetMapping("/{userId}")
public ResponseEntity<Object> getUserScore(@PathVariable Long userId) {
    Optional<UserScore> userScore = userScoreService.getUserScore(userId);

    if (userScore.isPresent()) {
        Map<String, Object> response = new HashMap<>();
        response.put("score", userScore.get().getScore());  // 🔹 Returnerer riktig score
        return ResponseEntity.ok(response);
    } else {
        return ResponseEntity.status(404).body("Brukers score ikke funnet");
    }
}
    

    @PostMapping("/update")
    public ResponseEntity<String> updateScore(@RequestBody ScoreUpdateRequest request) {
        if (request == null || request.getUserId() == null) {
            return ResponseEntity.badRequest().body("Feil: Mangler userId eller request-data!");
        }
        
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
