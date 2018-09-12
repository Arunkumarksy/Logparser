package com.parser.exception;

/**
 * 
 * @author arunkumar.k
 * Log Parser Custom Exception
 */
public class ParserException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public ParserException() {
	}

	public ParserException(String message) {
		super(message);
	}

	public ParserException(Throwable cause) {
		super(cause);
	}

	public ParserException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
