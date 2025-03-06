package com.g1pro1000.greenCode.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.g1pro1000.greenCode.model.User;
import com.g1pro1000.greenCode.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // Injiserer BCrypt fra SecurityConfig

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

        // Hashe passordet før lagring
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
        return "Bruker registrert!";
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> loginUser(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user; // Returnerer brukeren hvis innlogging er vellykket
        } else {
            return Optional.empty(); // Returnerer tomt hvis feil passord eller bruker ikke finnes
        }
    }

}
