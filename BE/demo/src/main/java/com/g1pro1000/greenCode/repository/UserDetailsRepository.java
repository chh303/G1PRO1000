package com.g1pro1000.greenCode.repository; 

import org.springframework.data.jpa.repository.JpaRepository; // Importerer JpaRepository for å håndtere databaseoperasjoner
import org.springframework.stereotype.Repository; // Importerer UserDetails-modellen som representerer en rad i databasen

import com.g1pro1000.greenCode.model.UserDetails; // Importerer Optional for å håndtere potensielt tomme databaseforespørsler

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {
    UserDetails findByUser_Id(Long userId); // Merk: _ mellom User og Id
}