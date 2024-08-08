package com.wable.www.WableServer.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class EnvironmentLogger {

	private static final Logger logger = LoggerFactory.getLogger(EnvironmentLogger.class);

	@Autowired
	private Environment environment;

	@PostConstruct
	public void logEnvironmentVariables() {
		String dbUrl = environment.getProperty("spring.datasource.url");
		String dbUser = environment.getProperty("spring.datasource.username");
		String activeProfile = environment.getProperty("spring.profiles.active");

		logger.info("Active Profile: {}", activeProfile);
		logger.info("Database URL: {}", dbUrl);
		logger.info("Database Username: {}", dbUser);

		// 원하는 다른 환경 변수들도 로그로 남길 수 있습니다.
	}
}