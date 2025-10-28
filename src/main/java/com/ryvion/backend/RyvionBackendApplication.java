package com.ryvion.backend;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RyvionBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(RyvionBackendApplication.class, args);
	}

    @Bean
    public Dotenv dotenv() {
        return Dotenv.configure().load();
    }

    @PostConstruct
    public void init(Dotenv dotenv) {
        System.out.println("Ryvion loaded! API key status: " +
                (dotenv.get("ANTHROPIC_API_KEY") != null ? "Ready" : "Set .env"));
    }
}
