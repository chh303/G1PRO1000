package com.g1pro1000.greenCode.controller; // Angir hvilken pakke denne klassen tilhører

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import com.g1pro1000.greenCode.model.User; // Importerer User-modellen som representerer en bruker
import com.g1pro1000.greenCode.service.UserService; // Importerer UserService for å håndtere forretningslogikk

/**
 * UserController håndterer HTTP-forespørsler relatert til brukere.
 * Den tilbyr API-endepunkter for registrering, innlogging og henting av brukere.
 */
@RestController // Forteller Spring Boot at dette er en REST-kontroller som håndterer API-forespørsler
@RequestMapping("/api/users") // Setter opp en grunn-URL for alle forespørsler til denne kontrolleren
public class UserController {

    @Autowired // Automatisk injisering av UserService, slik at vi kan bruke tjenestemetodene
    private UserService userService;

    /**
     * Endepunkt for å registrere en ny bruker.
     * Mottar JSON-data i request body, sender den videre til UserService, og returnerer et svar.
     * @param user Brukerobjekt med registreringsdata
     * @return ResponseEntity med en melding om registreringen var vellykket eller ikke
     */
    @PostMapping("/register") // Håndterer HTTP POST-forespørsler til /api/users/register
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        String response = userService.registerUser(user); // Kaller UserService for å registrere brukeren
        return ResponseEntity.ok(response); // Returnerer en HTTP 200 OK med svarmelding
    }

    /**
     * Endepunkt for å hente en bruker basert på brukernavn.
     * @param username Brukernavn til brukeren som skal hentes
     * @return ResponseEntity med brukerens data eller en feilmelding hvis brukeren ikke finnes
     */
    @GetMapping("/{username}") // Håndterer HTTP GET-forespørsler til /api/users/{username}
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        Optional<User> user = userService.getUserByUsername(username); // Henter bruker fra databasen via UserService
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get()); // Returnerer HTTP 200 OK med brukerens data
        } else {
            return ResponseEntity.status(404).body("Bruker ikke funnet"); // Returnerer HTTP 404 hvis brukeren ikke eksisterer
        }
    }

    /**
     * Endepunkt for innlogging.
     * Sjekker om brukernavn og passord er gyldige.
     * @param user Brukerobjekt med brukernavn og passord
     * @return ResponseEntity med melding om innlogging var vellykket eller ikke
     */
    @PostMapping("/login") // Håndterer HTTP POST-forespørsler til /api/users/login
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        String response = userService.loginUser(user.getUsername(), user.getPassword()); // Kaller UserService for å validere brukeren
        return ResponseEntity.ok(response); // Returnerer HTTP 200 OK med innloggingsstatus
    }
}
