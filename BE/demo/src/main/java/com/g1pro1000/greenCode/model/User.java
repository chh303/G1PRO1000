package com.g1pro1000.greenCode.model; // Angir hvilken pakke denne klassen tilhører

import jakarta.persistence.*; // Importerer nødvendige annotasjoner for databasekartlegging

/**
 * Brukermodellen representerer en bruker i databasen.
 * Denne klassen blir kartlagt til en database-tabell ved hjelp av JPA.
 */
@Entity // Marker klassen som en database-entitet
@Table(name = "users") // Angir at denne entiteten lagres i "users"-tabellen i databasen
public class User {

    @Id // Marker dette feltet som primærnøkkel (unik identifikator)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Primærnøkkelen genereres automatisk av databasen
    private Long id;

    @Column(nullable = false, unique = true) // Brukernavn kan ikke være null og må være unikt
    private String username;

    @Column(nullable = false) // Passord kan ikke være null
    private String password;

    @Column(nullable = false, unique = true) // E-post kan ikke være null og må være unik
    private String email;

    @Column(nullable = false, unique = true) // Telefonnummer kan ikke være null og må være unikt
    private String phone;

    /**
     * Standard konstruktør kreves av JPA.
     * Brukes når vi vil opprette en tom bruker og sette verdier senere.
     */
    public User() {}

    /**
     * Konstruktør for å opprette en bruker med alle nødvendige felter.
     * @param username Brukernavn
     * @param password Passord
     * @param email E-postadresse
     * @param phone Telefonnummer
     */
    public User(String username, String password, String email, String phone) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    // GETTER- OG SETTER-METODER (Gir tilgang til private variabler)

    /**
     * Henter brukerens unike ID.
     * @return Brukerens ID
     */
    public Long getId() { return id; }

    /**
     * Henter brukernavnet.
     * @return Brukernavnet
     */
    public String getUsername() { return username; }

    /**
     * Setter et nytt brukernavn.
     * @param username Det nye brukernavnet
     */
    public void setUsername(String username) { this.username = username; }

    /**
     * Henter passordet.
     * @return Passordet (bør hashes for sikkerhet!)
     */
    public String getPassword() { return password; }

    /**
     * Setter et nytt passord.
     * @param password Det nye passordet (bør hashes før lagring!)
     */
    public void setPassword(String password) { this.password = password; }

    /**
     * Henter e-postadressen.
     * @return E-postadressen
     */
    public String getEmail() { return email; }

    /**
     * Setter en ny e-postadresse.
     * @param email Den nye e-postadressen
     */
    public void setEmail(String email) { this.email = email; }

    /**
     * Henter telefonnummeret.
     * @return Telefonnummeret
     */
    public String getPhone() { return phone; }

    /**
     * Setter et nytt telefonnummer.
     * @param phone Det nye telefonnummeret
     */
    public void setPhone(String phone) { this.phone = phone; }
}
