package com.example.spring.spring;

import com.example.spring.spring.parser.AppConfigParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // scheduling di Spring
public class Server {

	public static void main(String[] args) {
		SpringApplication.run(Server.class, args);

		AppConfigParser parser = new AppConfigParser("/home/dash/VsCodeProgect/uni/spring/spring/src/main/resources/config.xml");
		//AppConfigParser parser = new AppConfigParser("/home/dash/IdeaProjects/springTest/src/main/resources/config.xml");
		String nome=parser.getNomeApp();
		System.out.println(parser.getFascePerServizio().toString());
		System.out.println("\n*** Server '"+nome+"' Avviato! In ascolto su http://localhost:8080 ***\n");

	}

}
