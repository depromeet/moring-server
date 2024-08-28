package org.depromeet.sambad.moring.domain.event.application;

import java.util.Optional;

import org.depromeet.sambad.moring.domain.event.domain.EventMessageTemplate;
import org.depromeet.sambad.moring.domain.event.domain.EventType;

public interface EventMessageTemplateRepository {

	Optional<EventMessageTemplate> findByType(EventType type);
}
