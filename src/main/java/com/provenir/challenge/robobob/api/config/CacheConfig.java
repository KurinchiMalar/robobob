package com.provenir.challenge.robobob.api.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Cache configuration for the application
 * Enables Caching and provides CacheManager bean for caching the questions.
 */
@Configuration
@EnableCaching
public class CacheConfig {

    /**
     * Creates a ConcurrentMapCacheManager for caching the questions.
     * @return CacheManager instance configured with questionCache cache.
     */
    @Bean
    public CacheManager cacheManager(){
        return new ConcurrentMapCacheManager("questionCache");
    }
}
