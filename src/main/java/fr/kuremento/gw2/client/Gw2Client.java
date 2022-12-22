package fr.kuremento.gw2.client;

import fr.kuremento.gw2.exceptions.TechnicalException;
import fr.kuremento.gw2.models.Constants;
import fr.kuremento.gw2.web.rest.services.account.AccountService;
import fr.kuremento.gw2.web.rest.services.achievements.AchievementsService;
import fr.kuremento.gw2.web.rest.services.colors.ColorsService;
import fr.kuremento.gw2.web.rest.services.home.HomeService;
import fr.kuremento.gw2.web.rest.services.items.ItemsService;
import fr.kuremento.gw2.web.rest.services.minis.MinisService;
import fr.kuremento.gw2.web.rest.services.quaggans.QuaggansService;
import fr.kuremento.gw2.web.rest.services.raids.RaidsService;
import fr.kuremento.gw2.web.rest.services.traits.TraitsService;
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
	private final HomeService homeService;
	private final MinisService minisService;
	private final RaidsService raidsService;
	private final ItemsService itemsService;
	private final TraitsService traitsService;

	public static Mono<Throwable> getErrorConsumerForError401(ClientResponse response) {
		return Mono.error(new TechnicalException(response.statusCode() + " : " + Constants.ERROR_401_403_MESSAGE));
	}

	public static Mono<Throwable> getErrorConsumerForError403(ClientResponse response) {
		return Mono.error(new TechnicalException(response.statusCode() + " : " + Constants.ERROR_401_403_MESSAGE));
	}

	public static Mono<Throwable> getErrorConsumerForError404(ClientResponse response) {
		return Mono.error(new TechnicalException(response.statusCode() + " : " + Constants.ERROR_404_MESSAGE));
	}

	public static Mono<Throwable> getErrorConsumerForError503(ClientResponse response) {
		return Mono.error(new TechnicalException(response.statusCode() + " : " + Constants.ERROR_503_MESSAGE));
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

	public HomeService home() {
		return this.homeService;
	}

	public MinisService minis() {
		return this.minisService;
	}

	public RaidsService raids() {
		return this.raidsService;
	}

	public ItemsService items() {
		return this.itemsService;
	}

	public TraitsService traits() {
		return this.traitsService;
	}

}
