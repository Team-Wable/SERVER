package com.wable.www.WableServer.common.config.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "admin-config")
public class AdminConfig {

	private List<Long> allowedIds;

	public List<Long> getAllowedIds() {
		return allowedIds;
	}

	public void setAllowedIds(List<Long> allowedIds) {
		this.allowedIds = allowedIds;
	}
}