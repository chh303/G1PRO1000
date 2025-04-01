// NewsletterController.java
package com.g1pro1000.greenCode.controller;

import com.g1pro1000.greenCode.model.NewsletterSubscriber;
import com.g1pro1000.greenCode.repository.NewsletterSubscriberRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/newsletter")
public class NewsletterController {

    private final NewsletterSubscriberRepository repository;

    public NewsletterController(NewsletterSubscriberRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribe(@RequestBody Map<String, String> body) {
        String email = body.get("email");

        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest().body("E-post er påkrevd");
        }

        boolean alreadyExists = repository.findByEmail(email).isPresent();
        if (alreadyExists) {
            return ResponseEntity.status(409).body("E-post er allerede påmeldt");
        }

        repository.save(new NewsletterSubscriber(email));
        return ResponseEntity.ok("Påmelding fullført");
    }
    @GetMapping("/all")
    public List<NewsletterSubscriber> getAllSubscribers() {
        return repository.findAll();
    }

}
