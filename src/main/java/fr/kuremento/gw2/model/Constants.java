package fr.kuremento.gw2.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Constants {

	ERROR_401_403_MESSAGE("The requested endpoint is authenticated and you did not provide a valid API key, or a valid API key without the necessary permissions"),
	ERROR_404_MESSAGE("The requested endpoint does not exist, or all of the provided IDs are invalid"),
	ERROR_503_MESSAGE("The requested endpoint is disabled");

	private final String value;
}
