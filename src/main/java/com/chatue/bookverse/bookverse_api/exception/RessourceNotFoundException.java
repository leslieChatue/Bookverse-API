package com.chatue.bookverse.bookverse_api.exception;

public class RessourceNotFoundException extends RuntimeException {

	/**
	 * Erreur 404
	 */
	private static final long serialVersionUID = 1L;

	public RessourceNotFoundException(String message) {
		 super(message);
	}
}
