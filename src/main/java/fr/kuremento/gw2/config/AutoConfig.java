package fr.kuremento.gw2.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration("gw2-api-config")
@ComponentScan(basePackages = {"fr.kuremento.gw2"})
public class AutoConfig {

	public AutoConfig() {
		log.debug("Gw2 API client loaded");
	}
}
