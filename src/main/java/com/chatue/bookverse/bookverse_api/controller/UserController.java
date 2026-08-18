package com.chatue.bookverse.bookverse_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chatue.bookverse.bookverse_api.dto.PaiementResponseDTO;
import com.chatue.bookverse.bookverse_api.dto.UserResponseDTO;
import com.chatue.bookverse.bookverse_api.dto.request.UserRequest;
import com.chatue.bookverse.bookverse_api.exception.ErrorResponse;
import com.chatue.bookverse.bookverse_api.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/users")
@Tag(name = "Users", description = "Api des Users")
public class UserController {

	private final UserService userService;


	@GetMapping("/{id}")
	@Operation(summary = "Recherche par id", description = "Api qui permet de trouver un user trié par id")
			@ApiResponse(responseCode = "200", description = "User trouvé", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class)))
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<UserResponseDTO> trouverUnuser( @PathVariable Long id) {
		return ResponseEntity.status(200).body(userService.getById(id));
	}

	@PostMapping
	@Operation(summary = "Enregister un user", description = "Api qui permet d'enregistrer un user ", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserRequest.class))))
	@ApiResponse(responseCode = "200", description = "Enregistrement réussi", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class)))
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<Void> enregistrerUser(@Valid @RequestBody UserRequest userRequest) {
		userService.saveUser(userRequest);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	/**
	 * Lister
	GET /api/users
	 **/
	@GetMapping
	@Operation(summary = "Recherche de tous les users", description = "Api qui permet de lister tous les users ")
			@ApiResponse(responseCode = "200", description = "Liste retournée contenant tous les users", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class)))
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<List<UserResponseDTO>> trouverToutesLesCommandes() {
		return ResponseEntity.status(200).body(userService.getAllUsers());
	}
	/**
	Modifier un user
	 */
	@PatchMapping("/{id}/refund")
	@Operation(summary = "Modifier un user", description = "Api qui permet de modifier le username d'un user")
			@ApiResponse(responseCode = "200", description = "Modification réussie")
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<UserResponseDTO> remboursement( @PathVariable Long id,@RequestParam String username) {
		return ResponseEntity.status(200).body(userService.updateUserByUsername(id ,username));
	}

}
