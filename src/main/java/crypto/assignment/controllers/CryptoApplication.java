package crypto.assignment.controllers;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"crypto.assignment"})
public class CryptoApplication {

	public static void main(String[] args) {
		org.springframework.boot.SpringApplication.run(CryptoApplication.class, args);
	}

}
