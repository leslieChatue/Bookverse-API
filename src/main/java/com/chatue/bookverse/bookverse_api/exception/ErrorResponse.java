package com.chatue.bookverse.bookverse_api.exception;


import java.time.LocalDateTime;

import lombok.Builder;
/**
 * Classe génerique de retour de message
 */
@Builder
public record ErrorResponse (String message,int statusCode,LocalDateTime heure,	String path) {
	
}
