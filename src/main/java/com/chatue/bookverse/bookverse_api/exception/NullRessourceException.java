package com.chatue.bookverse.bookverse_api.exception;

public class NullRessourceException extends RuntimeException {

	/**
	 * Erreur lorsque la liste renvoyée est nulle ou l'obejt retourné est null
	 */
	private static final long serialVersionUID = 1L;
	
	public NullRessourceException(String message) {
		super(message);
	}

}
