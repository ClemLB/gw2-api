package fr.kuremento.gw2.web.rest.services;

import fr.kuremento.gw2.client.Gw2Client;
import fr.kuremento.gw2.exceptions.TooManyArgumentsException;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Getter(AccessLevel.PROTECTED)
public abstract class AbstractService {

	private WebClient webClient;
	private Integer pageMaximumSize;
	private String baseUrl;

	@Autowired
	public final void setWebClient(@Qualifier("webclient-gw2") WebClient webClient) {
		this.webClient = webClient;
	}

	@Autowired
	public final void setPageMaximumSize(@Value("${application.rest.config.page-maximum-size}") Integer pageMaximumSize) {
		this.pageMaximumSize = pageMaximumSize;
	}

	@Autowired
	public final void setBaseUrl(@Value("${application.rest.config.base-url}") String baseUrl) {
		this.baseUrl = baseUrl;
	}

	protected <T> T get(URI uri, ParameterizedTypeReference<T> paramz) {
		return this.getWebClient()
				   .get()
				   .uri(uri)
				   .retrieve()
				   .onStatus(HttpStatus.UNAUTHORIZED::equals, Gw2Client::getErrorConsumerForError401)
				   .onStatus(HttpStatus.FORBIDDEN::equals, Gw2Client::getErrorConsumerForError403)
				   .onStatus(HttpStatus.NOT_FOUND::equals, Gw2Client::getErrorConsumerForError404)
				   .onStatus(HttpStatus.SERVICE_UNAVAILABLE::equals, Gw2Client::getErrorConsumerForError503)
				   .bodyToMono(paramz)
				   .block();
	}

	protected <T> T getWithAuthentification(URI uri, ParameterizedTypeReference<T> paramz, String apiKey) {
		return this.getWebClient()
				   .get()
				   .uri(uri)
				   .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
				   .retrieve()
				   .onStatus(HttpStatus.UNAUTHORIZED::equals, Gw2Client::getErrorConsumerForError401)
				   .onStatus(HttpStatus.FORBIDDEN::equals, Gw2Client::getErrorConsumerForError403)
				   .onStatus(HttpStatus.NOT_FOUND::equals, Gw2Client::getErrorConsumerForError404)
				   .onStatus(HttpStatus.SERVICE_UNAVAILABLE::equals, Gw2Client::getErrorConsumerForError503)
				   .bodyToMono(paramz)
				   .block();
	}

	protected <T> URI buildURI(String endpoint, T id) {
		return new DefaultUriBuilderFactory(this.getBaseUrl()).builder().path(endpoint).pathSegment(String.valueOf(id)).build();
	}

	protected <T> URI buildURI(String endpoint, List<T> ids) throws TooManyArgumentsException {
		if (ids.size() > this.getPageMaximumSize()) {
			String errorMessage = String.format("Maximum number of arguments is %d, you gave %d arguments, use 'getAll' method instead", this.getPageMaximumSize(),
												ids.size());
			throw new TooManyArgumentsException(errorMessage);
		}
		String formattedIds = ids.stream().map(String::valueOf).collect(Collectors.joining(","));
		return new DefaultUriBuilderFactory(this.getBaseUrl()).builder().path(endpoint).queryParam("ids", formattedIds).build();
	}

	protected URI buildURIAllIds(String endpoint) {
		return new DefaultUriBuilderFactory(this.getBaseUrl()).builder().path(endpoint).queryParam("ids", "all").build();
	}

	protected URI buildURI(String endpoint) {
		return new DefaultUriBuilderFactory(this.getBaseUrl()).builder().path(endpoint).build();
	}

	protected URI buildURIWithParams(String endpoint, String... params) {
		return new DefaultUriBuilderFactory(this.getBaseUrl()).builder().path(endpoint).build(params);
	}
}
