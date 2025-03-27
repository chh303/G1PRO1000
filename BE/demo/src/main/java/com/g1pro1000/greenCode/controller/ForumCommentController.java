package com.g1pro1000.greenCode.controller;

import com.g1pro1000.greenCode.model.ForumComment;
import com.g1pro1000.greenCode.model.ForumPost;
import com.g1pro1000.greenCode.repository.ForumCommentRepository;
import com.g1pro1000.greenCode.repository.ForumPostRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
public class ForumCommentController {

    @Autowired
    private ForumCommentRepository commentRepo;

    @Autowired
    private ForumPostRepository postRepo;

    // Legg til kommentar til et innlegg
    @PostMapping("/add/{postId}")
    public ForumComment addComment(@PathVariable Long postId, @RequestBody String content, HttpSession session) {
        ForumPost post = postRepo.findById(postId).orElseThrow(() -> new RuntimeException("Innlegg ikke funnet"));
        String author = (String) session.getAttribute("username");

        if (author == null) {
            throw new RuntimeException("Du må være logget inn for å kommentere.");
        }

        ForumComment comment = new ForumComment(post, content, author);
        return commentRepo.save(comment);
    }

    // Hent alle kommentarer til et innlegg
    @GetMapping("/post/{postId}")
    public List<ForumComment> getComments(@PathVariable Long postId) {
        return commentRepo.findByPostId(postId);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) return ResponseEntity.status(401).build();

        Optional<ForumComment> comment = commentRepo.findById(id);
        if (comment.isEmpty()) return ResponseEntity.notFound().build();
        if (!comment.get().getAuthor().equals(username)) return ResponseEntity.status(403).build();

        commentRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
