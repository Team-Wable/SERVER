package com.wable.www.WableServer.common.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

@Component
public class EnvironmentLogger implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(EnvironmentLogger.class);

	private final Environment environment;

	public EnvironmentLogger(Environment environment) {
		this.environment = environment;
	}

	@Override
	public void run(String... args) throws Exception {
		String dbUrl = environment.getProperty("spring.datasource.url");
		String dbUser = environment.getProperty("spring.datasource.username");
		String dbPw = environment.getProperty("spring.datasource.password");
		String activeProfile = environment.getProperty("spring.profiles.active");

		System.out.println("Active Profile: " + activeProfile);
		System.out.println("Database URL: " + dbUrl);
		System.out.println("Database Username: " + dbUser);
		System.out.println("Database Passeord: " + dbPw);
	}
}