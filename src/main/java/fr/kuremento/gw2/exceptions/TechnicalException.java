package fr.kuremento.gw2.exceptions;

import java.io.Serial;

/**
 * Exception technique
 */
public class TechnicalException extends RuntimeException {

	/**
	 *
	 */
	@Serial
	private static final long serialVersionUID = 1L;

	public TechnicalException() {
		super();
	}

	public TechnicalException(String message) {
		super(message);
	}

	public TechnicalException(Throwable cause) {
		super(cause);
	}

	public TechnicalException(String message, Throwable cause) {
		super(message, cause);
	}

	public TechnicalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
