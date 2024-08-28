package org.depromeet.sambad.moring.globalutils.logging;

import java.util.Locale;

import org.hibernate.engine.jdbc.internal.FormatStyle;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

public class P6spySqlLoggingFormatter implements MessageFormattingStrategy {

	@Override
	public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared,
		String sql, String url) {
		sql = formatSql(category, sql);
		return String.format(
			"[SQL] %s | %d ms | connectionId = %d %s", category, elapsed, connectionId, formatSql(category, sql));
	}

	private String formatSql(String category, String sql) {
		if (isEmpty(sql) || isNotStatement(category)) {
			return sql;
		}

		String trimmedSql = sql.trim().toLowerCase(Locale.ROOT);
		if (isDDL(trimmedSql)) {
			return FormatStyle.DDL.getFormatter().format(sql);
		}
		return FormatStyle.BASIC.getFormatter().format(sql);
	}

	private boolean isEmpty(String sql) {
		return sql == null || sql.trim().isEmpty();
	}

	private boolean isNotStatement(String category) {
		return !Category.STATEMENT.getName().equals(category);
	}

	private boolean isDDL(String sql) {
		return sql.startsWith("create") || sql.startsWith("alter");
	}
}