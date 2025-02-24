package com.g1pro1000.greenCode.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/names")
public class NameController {

    private static final String FILE_PATH = "BE\\demo\\src\\main\\resources\\static\\names.txt";

    @PostMapping("/save")
    public String saveName(@RequestParam String name) {
        try (FileWriter skriver = new FileWriter(FILE_PATH, true)) {
            skriver.write(name + "\n");
            return "Navn lagret!";
        } catch (IOException e) {
            return "Feil ved lagring av navn: " + e.getMessage();
        } 
    }

    @GetMapping("/get")
    public List<String> getNames() {
        ArrayList<String> names = new ArrayList<>();
        File file = new File(FILE_PATH);

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                names.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            return List.of("Feil ved henting av navn: " + e.getMessage());
        }
        return names;
    }
}