package org.depromeet.sambad.moring;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class MoringApplication {

	private static final String TIMEZONE_KST = "Asia/Seoul";

	public static void main(String[] args) {
		SpringApplication.run(MoringApplication.class, args);
	}

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone(TIMEZONE_KST));
	}
}
