package com.example.mod_search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ModSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModSearchApplication.class, args);
	}

}
