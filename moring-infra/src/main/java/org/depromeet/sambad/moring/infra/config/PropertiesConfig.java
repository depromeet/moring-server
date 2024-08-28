package org.depromeet.sambad.moring.infra.config;

import org.depromeet.sambad.moring.infra.MoringConfig;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan(basePackages = "org.depromeet.sambad.moring")
public class PropertiesConfig implements MoringConfig {
}
