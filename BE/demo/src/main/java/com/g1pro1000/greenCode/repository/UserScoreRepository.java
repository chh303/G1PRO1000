package com.g1pro1000.greenCode.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.g1pro1000.greenCode.model.UserScore;

import java.util.Optional;
import java.util.List;

public interface UserScoreRepository extends JpaRepository<UserScore, Long> {
    Optional<UserScore> findByUserId(Long userId);
    List<UserScore> findAllByOrderByScoreDesc();
}
