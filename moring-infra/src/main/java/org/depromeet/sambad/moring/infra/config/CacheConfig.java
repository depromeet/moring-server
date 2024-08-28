package org.depromeet.sambad.moring.infra.config;

import java.util.List;

import org.depromeet.sambad.moring.infra.MoringConfig;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;

@EnableCaching
public class CacheConfig implements MoringConfig {

	@Bean
	public CacheManager cacheManager() {
		ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager();
		cacheManager.setAllowNullValues(false);
		cacheManager.setCacheNames(List.of("eventTemplates"));
		return cacheManager;
	}

}
