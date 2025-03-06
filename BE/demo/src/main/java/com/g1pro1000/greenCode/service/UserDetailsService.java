package com.g1pro1000.greenCode.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.g1pro1000.greenCode.model.UserDetails;
import com.g1pro1000.greenCode.repository.UserDetailsRepository;

@Service
public class UserDetailsService {
    

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsService.class);

    @Autowired
    private UserDetailsRepository userDetailsRepository;
    @Transactional
    public boolean increaseScore(Long userId, int amount) {
        logger.info("Forsøker å oppdatere score for brukerID: {}", userId);
    
        Optional<UserDetails> userDetailsOpt = Optional.ofNullable(userDetailsRepository.findByUser_Id(userId));
    
        if (userDetailsOpt.isPresent()) {
            UserDetails userDetails = userDetailsOpt.get();
            logger.info("Fant bruker {} med nåværende score: {}", userId, userDetails.getScore());
            userDetails.setScore(userDetails.getScore() + amount);
            userDetailsRepository.save(userDetails);
            logger.info("Ny score for bruker {}: {}", userId, userDetails.getScore());
            return true;
        }
    
        logger.error("Fant ikke bruker med ID: {}", userId);
        return false;
    }

    public UserDetails saveUserDetails(UserDetails userDetails) {
        return userDetailsRepository.save(userDetails);
    }

    public Optional<UserDetails> getUserDetailsByUserId(Long userId) {
        return Optional.ofNullable(userDetailsRepository.findByUser_Id(userId));
    }
}