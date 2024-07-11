package org.depromeet.sambad.moyeo.auth.domain;

public interface TokenGenerator {

    String generate(Long userSeq);
}
