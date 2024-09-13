package org.depromeet.sambad.moring.api;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class MoringApiApplication {

	private static final String TIMEZONE_KST = "Asia/Seoul";

	public static void main(String[] args) {
		SpringApplication.run(MoringApiApplication.class, args);
	}

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone(TIMEZONE_KST));
	}

}
