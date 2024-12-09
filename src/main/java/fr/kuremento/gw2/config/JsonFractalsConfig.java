package fr.kuremento.gw2.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.kuremento.gw2.models.fractals.JsonFractal;
import fr.kuremento.gw2.models.fractals.JsonInstabilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Slf4j
@Configuration
public class JsonFractalsConfig {

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
