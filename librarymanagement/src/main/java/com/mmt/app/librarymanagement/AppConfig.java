package com.mmt.app.librarymanagement;

import java.time.LocalDateTime;
import java.util.TimeZone;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {

	@Bean
	@Scope("prototype")
	public LocalDateTime getLocalDateTime() {
		return LocalDateTime.now(TimeZone.getTimeZone("Asia/Calcutta").toZoneId());
	}
}
