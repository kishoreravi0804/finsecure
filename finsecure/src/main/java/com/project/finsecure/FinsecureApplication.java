package com.project.finsecure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class FinsecureApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinsecureApplication.class, args);
		 BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("Admin@123"));
		System.out.println("hello");
	}

}
