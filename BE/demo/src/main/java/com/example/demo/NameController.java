package com.example.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/names")
public class NameController {

    private static final String FILE_PATH = "src/main/resources/static/names.txt";

    @PostMapping("/save")
    public String saveName(@RequestParam String name) {
        try {
            Files.write(Paths.get(FILE_PATH), (name + "\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            return "Navn lagret!";
        } catch (IOException e) {
            return "Feil ved lagring av navn: " + e.getMessage();
        }
    }

    @GetMapping("/get")
    public List<String> getNames() {
        try {
            return Files.readAllLines(Paths.get(FILE_PATH));
        } catch (IOException e) {
            return List.of("Feil ved henting av navn: " + e.getMessage());
        }
    }
}