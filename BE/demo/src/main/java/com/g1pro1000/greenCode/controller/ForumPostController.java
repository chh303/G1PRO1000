package com.g1pro1000.greenCode.controller;

import com.g1pro1000.greenCode.model.ForumPost;
import com.g1pro1000.greenCode.repository.ForumPostRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forum")
public class ForumPostController {

    @Autowired
    private ForumPostRepository forumPostRepository;

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
}
