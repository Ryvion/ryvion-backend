package com.ryvion.backend;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RyvionBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(RyvionBackendApplication.class, args);
	}

    @PostConstruct
    public void init() {
        Dotenv dotenv = Dotenv.configure().load();
        String key = dotenv.get("ANTHROPIC_API_KEY");
        System.out.println("Ryvion started! API key: " + (key != null && !key.isEmpty() ? "null" : key));
    }
}
