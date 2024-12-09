package fr.kuremento.gw2.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.kuremento.gw2.models.fractals.JsonFractal;
import fr.kuremento.gw2.models.fractals.JsonInstabilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;

@Slf4j
@Configuration
@EnableCaching
@EnableScheduling
public class JsonFractalsConfig {

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("dailies", "cms", "recs");
    }

    @Scheduled(cron = "0 0 1 * * *", zone = "Europe/Paris")
    @CacheEvict(cacheNames = {"dailies", "cms", "recs"}, allEntries = true)
    public void reportCacheEvict() {
        log.info("Purge des caches");
    }

    @Bean
    public JsonFractal getFractalData(@Value("classpath:data/mappings.json") Resource fractalData) throws IOException {
        return new ObjectMapper().readValue(fractalData.getInputStream(), new TypeReference<>() {
        });
    }

    @Bean
    public JsonInstabilities getInstaData(@Value("classpath:data/instabilities.json") Resource instabilities) throws IOException {
        return new ObjectMapper().readValue(instabilities.getInputStream(), new TypeReference<>() {
        });
    }
}
