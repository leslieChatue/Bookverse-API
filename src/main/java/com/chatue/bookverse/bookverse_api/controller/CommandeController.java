package com.chatue.bookverse.bookverse_api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chatue.bookverse.bookverse_api.dto.BodyResponse;
import com.chatue.bookverse.bookverse_api.dto.CommandeResponseDTO;
import com.chatue.bookverse.bookverse_api.dto.request.CommandeRequest;
import com.chatue.bookverse.bookverse_api.service.CommandeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Liste retournée contenant toutes les commandes", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommandeResponseDTO.class))),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public ResponseEntity<BodyResponse> trouverUneCommandeParId(@Valid @RequestParam(required = true) Long id) {
		BodyResponse bodyResponse = new BodyResponse();
		bodyResponse.getBody().put("data", commandeService.getCommandeById(id));
		return ResponseEntity.status(200).body(bodyResponse);

	}

	@GetMapping
	@Operation(summary = "Recherche de toutes les commandes", description = "Api qui permet de lister toutes les commandes ")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Liste retournée contenant toutes les commandes", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommandeResponseDTO.class))),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public ResponseEntity<BodyResponse> trouverToutesLesCommandes(@Valid @RequestParam(required = true) Long id) {
		BodyResponse bodyResponse = new BodyResponse();
		bodyResponse.getBody().put("data", commandeService.getAllCommandes());
		return ResponseEntity.status(200).body(bodyResponse);

	}
	
	@GetMapping("/users/{id}")
	@Operation(summary = "Recherche de toutes les commandes d'un utilisateur en particulier", description = "Api qui permet de lister toutes les commandes d'un utilisateur  ")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Liste retournée contenant toutes les commandes d'un user", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommandeResponseDTO.class))),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public ResponseEntity<BodyResponse> trouverToutesLesCommandesParUser(@Valid @RequestParam(required = true) Long idUser) {
		BodyResponse bodyResponse = new BodyResponse();
		bodyResponse.getBody().put("data", commandeService.getAllCommandesByUser(idUser));
		return ResponseEntity.status(200).body(bodyResponse);

	}
	@PostMapping
	@Operation(summary = "Enregister une commande", description = "Api qui permet d'enregistrer une commande ", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommandeRequest.class))))
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Enregistrement réussi"),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public ResponseEntity<String> EnregistrerCommande(@Valid @RequestBody CommandeRequest commandeRequest) {
		commandeService.saveCommande(commandeRequest.getPanierId());
		return ResponseEntity.status(HttpStatus.CREATED).body("Livre enregistrée avec success");

	}
	
	/*
	 * 
	 * Modifier uniquement la quantité PATCH /api/livres/{id}/stock
	 */
	@PatchMapping("/{id}/statut")
	@Operation(summary = "Modifier le statut d'une commande", description = "Api qui permet de modifier uniquement le statut d'une commande", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))))
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Modification réussie"),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public ResponseEntity<String> ModifierCommande(@Valid @RequestParam(required = true) Long id,@Valid @RequestBody String statut) {
		commandeService.updateStatut(id ,statut);
		return ResponseEntity.ok("Modification partielle enregistrée avec success");
	}

	/*
	 * Supprimer DELETE /api/livres/{id}
	 */
	@DeleteMapping("/{id}")
	@Operation(summary = "Supprimer une commande ", description = "Api qui permet de supprimier une commande")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Suppréssion réussie"),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public ResponseEntity<String> SupprimerCommande(@Valid @RequestParam(required = true) Long id) {
		commandeService.deleteCommande(id );
		return ResponseEntity.ok("Suppréssion enregistrée avec success");
	}

	
	
	
	

}
