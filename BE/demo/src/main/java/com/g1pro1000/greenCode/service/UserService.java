package com.g1pro1000.greenCode.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.g1pro1000.greenCode.model.User;
import com.g1pro1000.greenCode.model.UserScore;
import com.g1pro1000.greenCode.repository.UserRepository;
import com.g1pro1000.greenCode.repository.UserScoreRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserScoreRepository userScoreRepository; // 🔹 FIKSET: Nå blir UserScoreRepository injisert riktig

    @Autowired
    private PasswordEncoder passwordEncoder; // 🔹 Bruker riktig definert PasswordEncoder

    public String registerUser(User user) {
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            return "Brukernavn er allerede i bruk!";
        }

        Optional<User> existingEmail = userRepository.findByEmail(user.getEmail());
        if (existingEmail.isPresent()) {
            return "E-post er allerede registrert!";
        }

        Optional<User> existingPhone = userRepository.findByPhone(user.getPhone());
        if (existingPhone.isPresent()) {
            return "Telefon nr er allerede registrert!";
        }

        // 🔹 Hashe passordet før vi lagrer det i databasen
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 🔹 Lagre brukeren i databasen
        User savedUser = userRepository.save(user);

        // 🔹 Opprett en ny score-rad i user_scores-tabellen
        UserScore userScore = new UserScore(savedUser);
        userScore.setScore(0); // Starter med 0 poeng
        userScoreRepository.save(userScore); // 🔹 Nå vil dette ikke lenger være rød

        return "Bruker registrert!";
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> loginUser(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user;
        } else {
            return Optional.empty();
        }
    }
}
