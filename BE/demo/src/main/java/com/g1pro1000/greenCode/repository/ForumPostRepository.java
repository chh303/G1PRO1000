package com.g1pro1000.greenCode.repository;

import com.g1pro1000.greenCode.model.ForumPost;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.g1pro1000.greenCode.repository.ForumPostRepository;

public interface ForumPostRepository extends JpaRepository<ForumPost, Long> {
    void deleteByAuthor(String author);
    List<ForumPost> findByAuthor(String author);
}
