package com.trader.core.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@Configuration
@EnableCaching
@EnableScheduling
public class BinanceCacheConfig {
    public static final String CACHE_NAME = "binance";
    private static final Logger LOG = LoggerFactory.getLogger(BinanceCacheConfig.class);

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(CACHE_NAME);
    }

    @Scheduled(cron = "#{'${spring.cache.evict-cron}'}")
    @CacheEvict(
            allEntries = true,
            value = {
                    CACHE_NAME
            })
    public void reportCacheEvict() {
        LOG.debug("EVICT BINANCE CACHE AT " + new Date());
    }
}
