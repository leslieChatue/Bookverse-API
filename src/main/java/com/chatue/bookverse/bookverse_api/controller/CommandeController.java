package com.chatue.bookverse.bookverse_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatue.bookverse.bookverse_api.dto.CommandeResponseDTO;
import com.chatue.bookverse.bookverse_api.dto.request.CommandeRequest;
import com.chatue.bookverse.bookverse_api.dto.request.PaiementRequest;
import com.chatue.bookverse.bookverse_api.dto.request.UpdateCommandRequest;
import com.chatue.bookverse.bookverse_api.exception.ErrorResponse;
import com.chatue.bookverse.bookverse_api.service.CommandeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/commandes")
@Tag(name = "Commandes", description = "Api des Commandes")
public class CommandeController {

	private final CommandeService commandeService;


	@GetMapping("/{id}")
	@Operation(summary = "Recherche par id", description = "Api qui permet de lister toutes les commandes filtrée par id ")
			@ApiResponse(responseCode = "200", description = "Liste retournée contenant toutes les commandes", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommandeResponseDTO.class)))
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<CommandeResponseDTO> trouverUneCommandeParId(@PathVariable Long id) {
		
		return ResponseEntity.status(200).body(commandeService.getCommandeById(id));

	}

	@GetMapping
	@Operation(summary = "Recherche de toutes les commandes", description = "Api qui permet de lister toutes les commandes ")
			@ApiResponse(responseCode = "200", description = "Liste retournée contenant toutes les commandes", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommandeResponseDTO.class)))
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<List<CommandeResponseDTO>> trouverToutesLesCommandes() {
		return ResponseEntity.status(200).body(commandeService.getAllCommandes());

	}
	
	@GetMapping("/users/{id}")
	@Operation(summary = "Recherche de toutes les commandes d'un utilisateur en particulier", description = "Api qui permet de lister toutes les commandes d'un utilisateur  ")
			@ApiResponse(responseCode = "200", description = "Liste retournée contenant toutes les commandes d'un user", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommandeResponseDTO.class)))
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<List<CommandeResponseDTO>> trouverToutesLesCommandesParUser(@PathVariable Long id) {
		return ResponseEntity.status(200).body(commandeService.getAllCommandesByUserId(id));

	}
	@PostMapping
	@Operation(summary = "Enregister une commande", description = "Api qui permet d'enregistrer une commande ", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommandeRequest.class))))
			@ApiResponse(responseCode = "200", description = "Enregistrement réussi",content=@Content(mediaType = "application/json", schema = @Schema(implementation = CommandeResponseDTO.class)))
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<CommandeResponseDTO> enregistrerCommande(@Valid @RequestBody CommandeRequest commandeRequest,@Valid @RequestBody PaiementRequest paiementRequest) {
		return ResponseEntity.status(HttpStatus.CREATED).body(commandeService.saveCommande(commandeRequest.getPanierId(), paiementRequest));
	}
	
	/*
	 * 
	 * Modifier uniquement la quantité PATCH /api/livres/{id}/stock
	 */
	@PatchMapping("/{id}/statut")
	@Operation(summary = "Modifier le statut d'une commande", description = "Api qui permet de modifier uniquement le statut d'une commande", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))))
			@ApiResponse(responseCode = "200", description = "Modification réussie",content=@Content(mediaType = "application/json", schema = @Schema(implementation = CommandeResponseDTO.class)))
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<CommandeResponseDTO> modifierCommande( @PathVariable Long id,@Valid @RequestBody UpdateCommandRequest updateCommandRequest) {
		return ResponseEntity.status(HttpStatus.OK).body(commandeService.updateStatut(id ,updateCommandRequest));
	}

	/*
	 * Supprimer DELETE /api/livres/{id}
	 */
	@DeleteMapping("/{id}")
	@Operation(summary = "Supprimer une commande ", description = "Api qui permet de supprimier une commande")
			@ApiResponse(responseCode = "204", description = "Suppréssion réussie")
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<Void> supprimerCommande(@PathVariable Long id) {
		commandeService.deleteCommande(id );
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}


}
