package com.g1pro1000.greenCode.repository; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; // Importerer JpaRepository for å håndtere databaseoperasjoner

import com.g1pro1000.greenCode.model.UserDetails; // Importerer UserDetails-modellen som representerer en rad i databasen

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {
 
    UserDetails findByUser_Id(Long userId); // Merk: _ mellom User og Id
}