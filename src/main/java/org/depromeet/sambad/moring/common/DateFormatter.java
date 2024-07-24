package org.depromeet.sambad.moring.common;

import static org.depromeet.sambad.moring.common.exception.GlobalExceptionCode.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.depromeet.sambad.moring.common.exception.BusinessException;

public class DateFormatter {

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public static String format(LocalDate date) {
		if (date == null) {
			throw new BusinessException(SERVER_ERROR);
		}
		return date.format(DATE_FORMATTER);
	}
}
