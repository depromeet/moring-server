package org.depromeet.sambad.moring.auth.domain;

public interface TokenGenerator {

	String generate(Long userId);
}
