package com.university.wizard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.university.wizard.repository")
public class WizardApplication {

	public static void main(String[] args) {
		SpringApplication.run(WizardApplication.class, args);
	}

}
