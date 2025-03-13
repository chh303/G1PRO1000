package com.g1pro1000.greenCode.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.g1pro1000.greenCode.model.User;
import com.g1pro1000.greenCode.model.UserScore;
import com.g1pro1000.greenCode.repository.UserRepository;
import com.g1pro1000.greenCode.repository.UserScoreRepository;

import java.util.Optional;
import java.util.List;

@Service
public class UserScoreService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserScoreRepository userScoreRepository;

    public String updateScore(Long userId, int score, List<String> achievements) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return "Bruker ikke funnet!";
        }

        // Henter eller oppretter en UserScore for brukeren
        UserScore userScore = userScoreRepository.findByUserId(userId)
                .orElseGet(() -> {
                    UserScore newUserScore = new UserScore(user.get());
                    newUserScore.setScore(0);
                    return newUserScore;
                });

        // Øker scoren
        userScore.setScore(userScore.getScore() + score);

        // Legger til achievements hvis de ikke allerede finnes
        for (String achievement : achievements) {
            if (!userScore.getAchievements().contains(achievement)) {
                userScore.addAchievement(achievement);
            }
        }

        userScoreRepository.save(userScore); // Lagrer den oppdaterte scoren
        return "Score oppdatert!";
    }

    public Optional<UserScore> getUserScore(Long userId) {
        return userScoreRepository.findByUserId(userId);
    }
     //Henter top 10 brukere basert på score
        public List<UserScore> getLeaderboard() {
            return userScoreRepository.findAllByOrderByScoreDesc();
    }
    
}
