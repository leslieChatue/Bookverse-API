package com.chatue.bookverse.bookverse_api.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalHandlerException {

	@ExceptionHandler(RessourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFound(HttpServletRequest request , RessourceNotFoundException ex){
		ErrorResponse err= new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value(), LocalDateTime.now(), request.getRequestURI());
		return new ResponseEntity<ErrorResponse>(err , HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(NullRessourceException.class)
	public ResponseEntity<ErrorResponse> handleNullExeption(HttpServletRequest request , NullRessourceException ex){
		ErrorResponse err= new ErrorResponse(ex.getMessage(), HttpStatus.ACCEPTED.value(), LocalDateTime.now(), request.getRequestURI());
		return new ResponseEntity<ErrorResponse>(err , HttpStatus.ACCEPTED);
		
	}
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorResponse> handleBadRequest (HttpServletRequest request , InternalErrorException ex){
		ErrorResponse err= new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), request.getRequestURI());
		return new ResponseEntity<ErrorResponse>(err , HttpStatus.BAD_REQUEST);
		
	}
	@ExceptionHandler(LivresExistException.class)
	public ResponseEntity<ErrorResponse> handleLivresExist(HttpServletRequest request , LivresExistException ex){
		ErrorResponse err= new ErrorResponse(ex.getMessage(), HttpStatus.CONFLICT.value(), LocalDateTime.now(), request.getRequestURI());
		return new ResponseEntity<ErrorResponse>(err , HttpStatus.CONFLICT);
		
	}
	@ExceptionHandler(RessourceExistException.class)
	public ResponseEntity<ErrorResponse> handleEmailsExist(HttpServletRequest request , RessourceExistException ex){
		ErrorResponse err= new ErrorResponse(ex.getMessage(), HttpStatus.CONFLICT.value(), LocalDateTime.now(), request.getRequestURI());
		return new ResponseEntity<ErrorResponse>(err , HttpStatus.CONFLICT);
		
	}
	@ExceptionHandler(InternalErrorException.class)
	public ResponseEntity<ErrorResponse> handleInternalExeption(HttpServletRequest request , RessourceExistException ex){
		ErrorResponse err= new ErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now(), request.getRequestURI());
		return new ResponseEntity<ErrorResponse>(err , HttpStatus.INTERNAL_SERVER_ERROR);
		
	}

}
