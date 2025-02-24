package com.g1pro1000.greenCode.service; // Angir at denne klassen tilhører "service"-pakken

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.g1pro1000.greenCode.model.User; // Importerer User-modellen som representerer en bruker i databasen
import com.g1pro1000.greenCode.repository.UserRepository; // Importerer UserRepository for databaseoperasjoner

import java.util.Optional;

/**
 * UserService håndterer forretningslogikken knyttet til brukere.
 * Den kommuniserer med UserRepository for å utføre operasjoner på databasen.
 */
@Service // Marker klassen som en Spring-tjeneste (Service) slik at den kan injiseres i andre klasser
public class UserService {

    @Autowired // Automatisk injisering av UserRepository slik at vi kan bruke den uten å opprette en ny instans manuelt
    private UserRepository userRepository;

    /**
     * Registrerer en ny bruker i databasen.
     * Før lagring sjekker vi om brukernavn, e-post eller telefonnummer allerede er i bruk.
     * 
     * @param user Brukerobjekt som inneholder registreringsdata
     * @return String - Meldingen om registreringen var vellykket eller ikke
     */
    public String registerUser(User user) {
        // Sjekker om brukernavn allerede finnes i databasen
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            return "Brukernavn er allerede i bruk!";
        }

        // Sjekker om e-post allerede finnes i databasen
        Optional<User> existingEmail = userRepository.findByEmail(user.getEmail());
        if (existingEmail.isPresent()) {
            return "E-post er allerede registrert!";
        }

        // Sjekker om telefonnummer allerede finnes i databasen
        Optional<User> existingPhone = userRepository.findByPhone(user.getPhone());
        if (existingPhone.isPresent()) {
            return "Telefon nr er allerede registrert!";
        }

        // Lagrer den nye brukeren i databasen
        userRepository.save(user);
        return "Bruker registrert!";
    }

    /**
     * Henter en bruker basert på brukernavn.
     * @param username Brukernavn til brukeren som skal hentes
     * @return Optional<User> - Returnerer en bruker hvis funnet, ellers tom Optional
     */
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Håndterer innlogging ved å sjekke om brukernavn og passord er gyldige.
     * 
     * @param username Brukernavnet til brukeren som prøver å logge inn
     * @param password Passordet brukeren oppgir
     * @return String - Melding om innlogging var vellykket eller ikke
     */
    public String loginUser(String username, String password) {
        // Sjekker om brukeren finnes i databasen basert på brukernavn
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) { // Hvis brukeren eksisterer
            if (user.get().getPassword().equals(password)) { // Sjekker om passordet er riktig
                return "Innlogging vellykket!";
            } else {
                return "Feil passord!"; // Returnerer feil hvis passordet ikke stemmer
            }
        } else {
            return "Bruker ikke funnet!"; // Returnerer feil hvis brukeren ikke finnes
        }
    }
}
