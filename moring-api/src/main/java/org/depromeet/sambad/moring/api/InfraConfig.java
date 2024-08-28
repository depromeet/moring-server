package org.depromeet.sambad.moring.api;

import org.depromeet.sambad.moring.infra.EnableMoringConfig;
import org.depromeet.sambad.moring.infra.MoringConfigGroup;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ComponentScan("org.depromeet.sambad.moring.domain")
@EnableMoringConfig({
	MoringConfigGroup.JPA,
	MoringConfigGroup.JPA_AUDITING,
	MoringConfigGroup.ASYNC,
	MoringConfigGroup.OBJECT_STORAGE,
	MoringConfigGroup.P6SPY,
	MoringConfigGroup.CONFIGURATION_PROPERTIES,
	MoringConfigGroup.SWAGGER,
	MoringConfigGroup.TIME,
	MoringConfigGroup.USER_ID_ARGUMENT_RESOLVER,
	MoringConfigGroup.SCHEDULING,
	MoringConfigGroup.SPRING_CACHE,
})
public class InfraConfig {
}
