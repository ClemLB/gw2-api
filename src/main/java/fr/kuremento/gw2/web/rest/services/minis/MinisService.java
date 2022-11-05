package fr.kuremento.gw2.web.rest.services.minis;

import fr.kuremento.gw2.web.rest.services.AbstractService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MinisService extends AbstractService {

	@Value("${application.rest.endpoints.minis}")
	private final String endpoint;

}
