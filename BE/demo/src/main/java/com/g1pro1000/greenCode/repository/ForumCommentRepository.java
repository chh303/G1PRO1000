package com.g1pro1000.greenCode.repository;

import com.g1pro1000.greenCode.model.ForumComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ForumCommentRepository extends JpaRepository<ForumComment, Long> {
    List<ForumComment> findByPostId(Long postId);
    void deleteByAuthor(String author);
    void deleteByPostId(Long postId);

}

