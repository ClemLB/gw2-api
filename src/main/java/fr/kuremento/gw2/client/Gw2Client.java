package fr.kuremento.gw2.client;

import fr.kuremento.gw2.exceptions.TechnicalException;
import fr.kuremento.gw2.web.rest.services.account.AccountService;
import fr.kuremento.gw2.web.rest.services.colors.ColorsService;
import fr.kuremento.gw2.web.rest.services.quaggans.QuaggansService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

@Component("gw2-client")
@RequiredArgsConstructor
public class Gw2Client {

	@Qualifier("account")
	private final AccountService accountService;

	@Qualifier("quaggans")
	private final QuaggansService quaggansService;

	@Qualifier("colors")
	private final ColorsService colorsService;

	public static Mono<? extends Throwable> getErrorConsumerForError401(ClientResponse response) {
		return Mono.error(new TechnicalException(
				"The requested endpoint is authenticated and you did not provide a valid API key, or a valid API key without the necessary permissions"));
	}

	public static Mono<? extends Throwable> getErrorConsumerForError403(ClientResponse response) {
		return Mono.error(new TechnicalException(
				"The requested endpoint is authenticated and you did not provide a valid API key, or a valid API key without the necessary permissions"));
	}

	public static Mono<? extends Throwable> getErrorConsumerForError404(ClientResponse response) {
		return Mono.error(new TechnicalException("The requested endpoint does not exist, or all of the provided IDs are invalid"));
	}

	public static Mono<? extends Throwable> getErrorConsumerForError503(ClientResponse response) {
		return Mono.error(new TechnicalException("The requested endpoint is disabled"));
	}

	public AccountService account() {
		return accountService;
	}

	public QuaggansService quaggans() {
		return quaggansService;
	}

	public ColorsService colors() {
		return colorsService;
	}

}
