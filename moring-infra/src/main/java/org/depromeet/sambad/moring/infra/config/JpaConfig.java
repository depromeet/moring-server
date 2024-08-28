package org.depromeet.sambad.moring.infra.config;

import org.depromeet.sambad.moring.infra.MoringConfig;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

@EntityScan(basePackages = "org.depromeet.sambad.moring")
@EnableJpaRepositories(basePackages = "org.depromeet.sambad.moring")
public class JpaConfig implements MoringConfig {

	private final EntityManager entityManager;

	public JpaConfig(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Bean
	public JPAQueryFactory jpaQueryFactory() {
		return new JPAQueryFactory(entityManager);
	}
}

