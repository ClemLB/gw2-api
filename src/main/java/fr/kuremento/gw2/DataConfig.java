package fr.kuremento.gw2;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.kuremento.gw2.models.fractals.JsonFractal;
import fr.kuremento.gw2.models.fractals.JsonInstabilities;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Configuration
public class DataConfig {

    @Bean
    public JsonFractal getFractalData(@Value("classpath:data/mappings.json") Resource fractalData) throws IOException {
        return new ObjectMapper().readValue(fractalData.getFile(), new TypeReference<>() {
        });
    }

    @Bean
    public JsonInstabilities getInstaData(@Value("classpath:data/instabilities.json") Resource instabilities) throws IOException {
        return new ObjectMapper().readValue(instabilities.getFile(), new TypeReference<>() {
        });
    }
}
