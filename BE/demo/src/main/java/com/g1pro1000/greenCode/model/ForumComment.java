package com.g1pro1000.greenCode.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ForumComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private ForumPost post;

    private String content;
    private String author;
    private LocalDateTime timestamp;

    public ForumComment() {}

    public ForumComment(ForumPost post, String content, String author) {
        this.post = post;
        this.content = content;
        this.author = author;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and setters
    public Long getId() { return id; }
    public ForumPost getPost() { return post; }
    public void setPost(ForumPost post) { this.post = post; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
