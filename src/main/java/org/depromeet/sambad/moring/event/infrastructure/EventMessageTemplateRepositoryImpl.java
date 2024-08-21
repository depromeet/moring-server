package org.depromeet.sambad.moring.event.infrastructure;

import java.util.Optional;

import org.depromeet.sambad.moring.common.logging.NoLogging;
import org.depromeet.sambad.moring.event.application.EventMessageTemplateRepository;
import org.depromeet.sambad.moring.event.domain.EventMessageTemplate;
import org.depromeet.sambad.moring.event.domain.EventType;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Repository
public class EventMessageTemplateRepositoryImpl implements EventMessageTemplateRepository {

	private final EventMessageTemplateJpaRepository eventMessageTemplateJpaRepository;

	@Override
	@Cacheable(value = "eventTemplates", key = "#type", unless = "#result == null")
	public Optional<EventMessageTemplate> findByType(EventType type) {
		return eventMessageTemplateJpaRepository.findByType(type);
	}

	@NoLogging
	@CacheEvict(value = "eventTemplates", allEntries = true)
	@Scheduled(fixedRateString = "${caching.spring.event-templates-ttl}")
	public void evictAllCaches() {
	}
}
