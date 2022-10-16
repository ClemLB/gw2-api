package fr.kuremento.gw2.exceptions;

public class TooManyArgumentsException extends Exception {

	public TooManyArgumentsException() {
		super();
	}

	public TooManyArgumentsException(String message) {
		super(message);
	}

	public TooManyArgumentsException(Throwable cause) {
		super(cause);
	}

	public TooManyArgumentsException(String message, Throwable cause) {
		super(message, cause);
	}

	public TooManyArgumentsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
