package com.example.spring.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		System.out.println("\n*** Server Semplice Avviato! In ascolto su http://localhost:8080 ***\n");
	}

}
