package com.g1pro1000.greenCode.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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

    // 🔹 Én til mange-forhold: ett innlegg har mange kommentarer
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ForumComment> comments = new ArrayList<>();

    public ForumPost() {}

    public ForumPost(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.timestamp = LocalDateTime.now();
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public String getAuthor() { return author; }

    public void setAuthor(String author) { this.author = author; }

    public LocalDateTime getTimestamp() { return timestamp; }

    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public List<ForumComment> getComments() { return comments; }

    public void setComments(List<ForumComment> comments) { this.comments = comments; }
}
