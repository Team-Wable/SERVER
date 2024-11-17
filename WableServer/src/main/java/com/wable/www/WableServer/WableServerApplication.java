package com.wable.www.WableServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WableServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WableServerApplication.class, args);
	}

}
