package org.depromeet.sambad.moring.infra.config;

import org.depromeet.sambad.moring.infra.MoringConfig;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
public class JpaAuditingConfig implements MoringConfig {
}
