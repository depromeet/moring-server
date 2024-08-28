package org.depromeet.sambad.moring.infra;

import org.depromeet.sambad.moring.infra.config.AsyncConfig;
import org.depromeet.sambad.moring.infra.config.CacheConfig;
import org.depromeet.sambad.moring.infra.config.JpaAuditingConfig;
import org.depromeet.sambad.moring.infra.config.JpaConfig;
import org.depromeet.sambad.moring.infra.config.ObjectStorageConfig;
import org.depromeet.sambad.moring.infra.config.P6spyConfig;
import org.depromeet.sambad.moring.infra.config.PropertiesConfig;
import org.depromeet.sambad.moring.infra.config.SchedulingConfig;
import org.depromeet.sambad.moring.infra.config.SwaggerConfig;
import org.depromeet.sambad.moring.infra.config.TimeConfig;
import org.depromeet.sambad.moring.infra.config.UserIdArgumentResolverConfig;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MoringConfigGroup {

	// TODO: 현재 해당 설정은 moring-domain 모듈에 존재하며, request/response 의존성을 api 모듈로 옮긴 후 infra 모듈로 이동해야 함
	// FULL_FILE_URL_ANNOTATION(FullFileUrlConfig.class),
	JPA(JpaConfig.class),
	JPA_AUDITING(JpaAuditingConfig.class),
	OBJECT_STORAGE(ObjectStorageConfig.class),
	P6SPY(P6spyConfig.class),
	CONFIGURATION_PROPERTIES(PropertiesConfig.class),
	SWAGGER(SwaggerConfig.class),
	TIME(TimeConfig.class),
	USER_ID_ARGUMENT_RESOLVER(UserIdArgumentResolverConfig.class),
	SCHEDULING(SchedulingConfig.class),
	SPRING_CACHE(CacheConfig.class),
	ASYNC(AsyncConfig.class),

	;

	private final Class<? extends MoringConfig> configClass;

}
