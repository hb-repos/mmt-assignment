package com.mmt.app.librarymanagement;

import java.time.LocalDate;
import java.util.TimeZone;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {

	@Bean
	@Scope("prototype")
	public LocalDate getLocalDateTime() {
		return LocalDate.now(TimeZone.getTimeZone("Asia/Calcutta").toZoneId());
	}
}
