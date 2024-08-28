package org.depromeet.sambad.moring.domain.event.infrastructure;

import java.util.Optional;

import org.depromeet.sambad.moring.domain.event.domain.EventMessageTemplate;
import org.depromeet.sambad.moring.domain.event.domain.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventMessageTemplateJpaRepository extends JpaRepository<EventMessageTemplate, Long> {

	Optional<EventMessageTemplate> findByType(EventType type);
}
