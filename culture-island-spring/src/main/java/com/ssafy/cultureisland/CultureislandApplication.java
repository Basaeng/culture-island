package com.ssafy.cultureisland;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class CultureislandApplication {

	public static void main(String[] args) {
		SpringApplication.run(CultureislandApplication.class, args);
	}

}
