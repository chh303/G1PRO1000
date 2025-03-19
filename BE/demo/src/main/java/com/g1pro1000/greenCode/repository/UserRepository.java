package com.g1pro1000.greenCode.repository; // Angir hvilken pakke denne filen tilhører

import java.util.Optional; // Importerer JpaRepository for å håndtere databaseoperasjoner

import org.springframework.data.jpa.repository.JpaRepository; // Importerer User-modellen som representerer en rad i databasen

import com.g1pro1000.greenCode.model.User; // Importerer Optional for å håndtere potensielt tomme databaseforespørsler

/**
 * UserRepository er ansvarlig for databaseoperasjoner relatert til User-entiteten.
 * Denne grensesnittet utvider JpaRepository, noe som gir tilgang til CRUD-operasjoner 
 * (Create, Read, Update, Delete) uten behov for å skrive SQL selv.
 */

public interface UserRepository extends JpaRepository<User, Long> { // JpaRepository håndterer databaseoperasjoner for User-tabellen

    /**
     * Søker etter en bruker basert på e-post.
     * @param email E-postadressen som søkes etter
     * @return Optional<User> - Returnerer en bruker hvis funnet, ellers tom Optional
     */
    Optional<User> findByEmail(String email);

    /**
     * Søker etter en bruker basert på brukernavn.
     * @param username Brukernavnet som søkes etter
     * @return Optional<User> - Returnerer en bruker hvis funnet, ellers tom Optional
     */
    Optional<User> findByUsername(String username);

    /**
     * Søker etter en bruker basert på telefonnummer.
     * @param phone Telefonnummeret som søkes etter
     * @return Optional<User> - Returnerer en bruker hvis funnet, ellers tom Optional
     */
    Optional<User> findByPhone(String phone);
}
