package org.depromeet.sambad.moring.common.response;

import static org.depromeet.sambad.moring.common.exception.GlobalExceptionCode.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class DateFormatter {

	private static final DateTimeFormatter DATE_FORMATTER = java.time.format.DateTimeFormatter.ofPattern(
		"yyyy-MM-dd");
	private static final DateTimeFormatter DATE_TIME_FORMATTER = java.time.format.DateTimeFormatter.ofPattern(
		"yyyy-MM-dd HH:mm:ss");

	public static String format(LocalDate date) {
		if (date == null) {
			throw new BusinessException(SERVER_ERROR);
		}
		return date.format(DATE_FORMATTER);
	}

	public static String format(LocalDateTime date) {
		if (date == null) {
			throw new BusinessException(SERVER_ERROR);
		}
		return date.format(DATE_TIME_FORMATTER);
	}
}
