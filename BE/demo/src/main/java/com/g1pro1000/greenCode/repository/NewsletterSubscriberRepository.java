// NewsletterSubscriberRepository.java
package com.g1pro1000.greenCode.repository;

import com.g1pro1000.greenCode.model.NewsletterSubscriber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsletterSubscriberRepository extends JpaRepository<NewsletterSubscriber, Long> {
    Optional<NewsletterSubscriber> findByEmail(String email);
}
