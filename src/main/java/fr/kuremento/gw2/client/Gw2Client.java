package fr.kuremento.gw2.client;

import fr.kuremento.gw2.exceptions.TechnicalException;
import fr.kuremento.gw2.web.rest.services.account.AccountService;
import fr.kuremento.gw2.web.rest.services.achievements.AchievementsService;
import fr.kuremento.gw2.web.rest.services.colors.ColorsService;
import fr.kuremento.gw2.web.rest.services.quaggans.QuaggansService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

@Component("gw2-client")
@RequiredArgsConstructor
public class Gw2Client {

	private final AccountService accountService;
	private final AchievementsService achievementsService;
	private final QuaggansService quaggansService;
	private final ColorsService colorsService;

	public static Mono<Throwable> getErrorConsumerForError401(ClientResponse response) {
		return Mono.error(new TechnicalException(response.statusCode() +
		                                         " : The requested endpoint is authenticated and you did not provide a valid API key, or a valid API key without the necessary permissions"));
	}

	public static Mono<Throwable> getErrorConsumerForError403(ClientResponse response) {
		return Mono.error(new TechnicalException(response.statusCode() +
		                                         " : The requested endpoint is authenticated and you did not provide a valid API key, or a valid API key without the necessary permissions"));
	}

	public static Mono<Throwable> getErrorConsumerForError404(ClientResponse response) {
		return Mono.error(new TechnicalException(response.statusCode() + " : The requested endpoint does not exist, or all of the provided IDs are invalid"));
	}

	public static Mono<Throwable> getErrorConsumerForError503(ClientResponse response) {
		return Mono.error(new TechnicalException(response.statusCode() + " : The requested endpoint is disabled"));
	}

	public AccountService account() {
		return this.accountService;
	}

	public AchievementsService achievements() {
		return this.achievementsService;
	}

	public QuaggansService quaggans() {
		return this.quaggansService;
	}

	public ColorsService colors() {
		return this.colorsService;
	}

}
