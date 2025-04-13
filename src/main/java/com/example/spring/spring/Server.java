package com.example.spring.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Server {

	public static void main(String[] args) {
		SpringApplication.run(Server.class, args);
		System.out.println("\n*** Server Avviato! In ascolto su http://localhost:8080 ***\n");
	}

}
