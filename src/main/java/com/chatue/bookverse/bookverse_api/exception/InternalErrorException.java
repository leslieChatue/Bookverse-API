package com.chatue.bookverse.bookverse_api.exception;

public class InternalErrorException extends RuntimeException {
	
	/**
	 * Erreur serveur interne 500
	 */
	private static final long serialVersionUID = 1L;

	public InternalErrorException(String message) {
		super(message);
	}

}
