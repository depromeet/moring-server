package org.depromeet.sambad.moring.event.application;

import java.util.Optional;

import org.depromeet.sambad.moring.event.domain.EventMessageTemplate;
import org.depromeet.sambad.moring.event.domain.EventType;

public interface EventMessageTemplateRepository {

	Optional<EventMessageTemplate> findByType(EventType type);
}
