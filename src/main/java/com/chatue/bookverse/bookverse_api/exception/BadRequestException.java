package com.chatue.bookverse.bookverse_api.exception;

public class BadRequestException extends RuntimeException {

	/**
	 * Erreur 403
	 */
	private static final long serialVersionUID = 1L;

	public BadRequestException(String message) {
		super(message);
	}
}
