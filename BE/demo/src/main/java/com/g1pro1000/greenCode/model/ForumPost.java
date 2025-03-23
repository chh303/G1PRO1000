package com.g1pro1000.greenCode.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ForumPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 1000)
    private String content;

    private String author;

    private LocalDateTime timestamp;

    // 🔹 Konstruktør uten argumenter
    public ForumPost() {}

    // 🔹 Konstruktør med tittel, innhold og forfatter
    public ForumPost(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.timestamp = LocalDateTime.now();
    }

    // 🔹 Getters og setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
