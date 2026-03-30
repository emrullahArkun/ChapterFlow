package com.example.mybooktracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MyBookTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyBookTrackerApplication.class, args);
	}

}
