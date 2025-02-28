/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.g1pro1000.greenCode.model;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column; // Importerer nødvendige annotasjoner for databasekartlegging
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
/**
 *
 * @author ch.h
 */
@Entity
@Table(name = "user_details")
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, unique = true)
    private User user;

    @Column(nullable = true) 
    private Long score; // Samlet poeng score av greencode spill.

    @Column(nullable = true)
    private String bookmark; // Bookmarkede artikler

    @Column(nullable = true)
    private String achievement; // Potensielle achievements???

    @Column(nullable = false, updatable = false)
    @CreationTimestamp // H2 støtter dette!
    private LocalDateTime createdAt;


    public UserDetails() {} // Standard konstruktør (kreves av JPA)

    /* Konstruktør for å opprette en UserDetails-instans. */
    public UserDetails(User user, Long score, String bookmark, String achievement) {
        this.user = user;
        this.score = score;
        this.bookmark = bookmark;
        this.achievement = achievement;
    }

    // GETTERS OG SETTERS

    public Long getId() { return id; }

    public User getUser() { return user; }
    public void setUser(User user) {this.user = user; }

    public Long getScore() { return score;}
    public void setScore(Long score) { this.score = score; }

    public String getBookmark() { return  bookmark; }
    public void setBookmark(String bookmark) { this.bookmark = bookmark; }

    public String getAchievement() { return  achievement; }
    public void setAchievement(String achievement) { this.achievement = achievement; }

    public LocalDateTime getCreatedAt() { return createdAt; }

}
