package fr.kuremento.gw2.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@EnableCaching
@EnableScheduling
public class CachingConfig {

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("dailies", "cms", "recs");
    }

    @Scheduled(cron = "0 0 1 * * *", zone = "Europe/Paris")
    @CacheEvict(cacheNames = {"dailies", "cms", "recs"}, allEntries = true)
    public void reportCacheEvict() {
        log.info("Purge des caches");
    }
}
