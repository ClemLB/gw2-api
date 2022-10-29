package fr.kuremento.gw2.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebClientConfig implements WebFluxConfigurer {

	private final ObjectMapper objectMapper;

	@Value("${application.rest.config.base-url}")
	private String baseUrl;
	@Value("${application.rest.config.enable-wiretap}")
	private Boolean enableWireTap;
	@Value("${application.rest.config.connection-timeout}")
	private Integer connectionTimeOut;
	@Value("${application.rest.config.read-timeout}")
	private Integer readTimeOut;
	@Value("${application.rest.config.write-timeout}")
	private Integer writeTimeOut;

	private ExchangeStrategies exchangeStrategies() {
		var encoder = new Jackson2JsonEncoder(objectMapper);
		var decoder = new Jackson2JsonDecoder(objectMapper);
		final int size = 16 * 1024 * 1024;
		return ExchangeStrategies.builder().codecs(configurer -> {
			configurer.defaultCodecs().jackson2JsonEncoder(encoder);
			configurer.defaultCodecs().jackson2JsonDecoder(decoder);
			configurer.defaultCodecs().maxInMemorySize(size);
		}).build();
	}

	private ExchangeFilterFunction logRequest() {
		return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
			if (log.isDebugEnabled()) {
				log.debug("Appel {} vers {}", clientRequest.method(), clientRequest.url());
			}
			return Mono.just(clientRequest);
		});
	}

	private ExchangeFilterFunction logResponse() {
		return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
			if (log.isDebugEnabled()) {
				log.debug("Retour appel avec code HTTP {}", clientResponse.statusCode());
			}
			return Mono.just(clientResponse);
		});
	}

	@Bean("webclient-builder")
	public WebClient.Builder webClientBuilder(@Value("${application.rest.config.schema-version-date}") String schemaVersionDate,
	                                          @Value("${application.rest.config.language:en}") String language) {
		var httpClient = HttpClient.create()
		                           .tcpConfiguration(client -> client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeOut)
		                                                             .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(readTimeOut))
		                                                                                        .addHandlerLast(new WriteTimeoutHandler(writeTimeOut))))
		                           .wiretap(enableWireTap);
		return WebClient.builder()
		                .exchangeStrategies(exchangeStrategies())
		                .filters(exchangeFilterFunctions -> {
			                exchangeFilterFunctions.add(logRequest());
			                exchangeFilterFunctions.add(logResponse());
		                })
		                .baseUrl(baseUrl)
		                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
		                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
		                .defaultHeader("X-Schema-Version", schemaVersionDate)
		                .defaultHeader(HttpHeaders.ACCEPT_LANGUAGE, language)
		                .clientConnector(new ReactorClientHttpConnector(httpClient));
	}

	@Bean("webclient-gw2")
	public WebClient webClient(@Qualifier("webclient-builder") WebClient.Builder builder) {
		return builder.build();
	}

}
