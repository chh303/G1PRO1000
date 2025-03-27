package com.g1pro1000.greenCode.controller;

import com.g1pro1000.greenCode.model.ForumPost;
import com.g1pro1000.greenCode.repository.ForumPostRepository;
import com.g1pro1000.greenCode.repository.ForumCommentRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/forum")
public class ForumPostController {

    @Autowired
    private ForumPostRepository forumPostRepository;
    @Autowired
    private ForumCommentRepository forumCommentRepository;


    // Opprett nytt innlegg
    @PostMapping("/create")
    public ForumPost createPost(@RequestBody ForumPost post, HttpSession session) {
        String author = (String) session.getAttribute("username");
        if (author == null) {
            throw new RuntimeException("Bruker må være logget inn for å poste.");
        }

        ForumPost newPost = new ForumPost(post.getTitle(), post.getContent(), author);
        return forumPostRepository.save(newPost);
    }

    // Hent alle innlegg
    @GetMapping("/all")
    public List<ForumPost> getAllPosts() {
        return forumPostRepository.findAll();
    }

    // Hent ett spesifikt innlegg
    @GetMapping("/{id}")
    public ForumPost getPostById(@PathVariable Long id) {
        return forumPostRepository.findById(id).orElseThrow(() -> new RuntimeException("Innlegg ikke funnet"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id, HttpServletRequest request) {
        String username = (String) request.getSession().getAttribute("username");
        Optional<ForumPost> post = forumPostRepository.findById(id);
    
        if (post.isEmpty()) return ResponseEntity.notFound().build();
        if (!post.get().getAuthor().equals(username)) return ResponseEntity.status(403).build();
    
        forumCommentRepository.deleteAll(post.get().getComments());
        forumPostRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
    

}
