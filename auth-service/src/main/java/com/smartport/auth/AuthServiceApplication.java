
package com.smartport.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

	@Bean
	ApplicationRunner printApplicationUrls() {
		return args -> {
			System.out.println("------------------------------------");
			System.out.println("Auth Service is running!");
			System.out.println("Server:  http://localhost:8081");
			System.out.println("Swagger: http://localhost:8081/swagger-ui/index.html");
			System.out.println("------------------------------------");
		};
	}
}
