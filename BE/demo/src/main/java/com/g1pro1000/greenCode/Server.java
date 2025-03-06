package com.g1pro1000.greenCode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Server {


	@GetMapping("/api/hello")
	public String sayHello(@RequestParam(value = "name", defaultValue = "verden") String name) {
		return "Hei, " + name + "! Serveren fungerer." + "\n" + "(Dette er en respons fra Server klassen.)";
	}

	public static void main(String[] args) {
		SpringApplication.run(Server.class, args);
		H2Query.getUserTotal();
	}
	
}