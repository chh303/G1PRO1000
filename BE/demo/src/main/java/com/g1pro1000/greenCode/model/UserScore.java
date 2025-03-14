package com.g1pro1000.greenCode.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_scores")
public class UserScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true) // Knytter til users-tabellen
    private User user;

    @Column(nullable = false)
    private int score = 0; // Standardverdi 0 hvis brukeren ikke har deltatt

    @ElementCollection // Hibernate håndterer dette som en liste
    @CollectionTable(name = "user_achievements", joinColumns = @JoinColumn(name = "user_score_id"))
    @Column(name = "achievement")
    private List<String> achievements = new ArrayList<>();

    public UserScore() {}

    public UserScore(User user) {
        this.user = user;
        this.score = 0; // Standardverdi
        this.achievements = new ArrayList<>();
    }

    public Long getId() { return id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public List<String> getAchievements() { return achievements; }
    public void addAchievement(String achievement) { this.achievements.add(achievement); }
}
