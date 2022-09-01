package ai.cryptocast.core.configs;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableCaching
@EnableScheduling
@EnableConfigurationProperties({BinanceProperties.class})
public class BinanceConfig {
    public static final String CACHE_NAME = "binance";

    private final BinanceProperties binanceProperties;

    public BinanceConfig(BinanceProperties binanceProperties) {
        this.binanceProperties = binanceProperties;
    }

    @Scheduled(cron = "#{'${spring.cache.evict-cron}'}")
    @CacheEvict(allEntries = true, value = {CACHE_NAME})
    public void report() {
        //
    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(CACHE_NAME);
    }

    public BinanceProperties getBinanceProperties() {
        return binanceProperties;
    }
}
