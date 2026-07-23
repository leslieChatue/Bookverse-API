package com.chatue.bookverse.bookverse_api.exception;


import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * Classe génerique de retour de message
 */
@AllArgsConstructor
@NoArgsConstructor @Getter @Setter
@EqualsAndHashCode
public class ErrorResponse {

	private String message;
	private int statusCode;
	private LocalDateTime heure;
	private String path;
}
