package com.chatue.bookverse.bookverse_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatue.bookverse.bookverse_api.dto.PaiementResponseDTO;
import com.chatue.bookverse.bookverse_api.dto.request.PaiementRequest;
import com.chatue.bookverse.bookverse_api.exception.ErrorResponse;
import com.chatue.bookverse.bookverse_api.service.PaiementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/paiements")
@Tag(name = "Paiments", description = "Api des Paiement")
public class PaiementController {

	private final PaiementService paiementService;


	@GetMapping("/{id}")
	@Operation(summary = "Recherche par id", description = "Api qui permet de trouver un paiement trié par id")
			@ApiResponse(responseCode = "200", description = "Paiement trouvé", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaiementResponseDTO.class)))
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<PaiementResponseDTO> trouverUnPaiement( @PathVariable Long id) {
		return ResponseEntity.status(200).body(paiementService.getPaiementById(id));
	}

	@PostMapping
	@Operation(summary = "Enregister un paiement", description = "Api qui permet d'enregistrer un paiement ", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaiementRequest.class))))
	@ApiResponse(responseCode = "200", description = "Enregistrement réussi", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaiementResponseDTO.class)))
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<PaiementResponseDTO> enregistrerLivre(@Valid @RequestBody PaiementRequest paiementRequest) {
		return ResponseEntity.status(HttpStatus.CREATED).body(paiementService.savePaiement(paiementRequest));
	}

	/**
	 * Lister
	GET /api/paiements
	 **/
	@GetMapping
	@Operation(summary = "Recherche de tous les paiements", description = "Api qui permet de lister tous les paiements ")
			@ApiResponse(responseCode = "200", description = "Liste retournée contenant tous les paiements", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaiementResponseDTO.class)))
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<List<PaiementResponseDTO>> trouverToutesLesCommandes() {
		return ResponseEntity.status(200).body(paiementService.getAllPaiements());
	}
	/**
	Remboursement (plus tard)
	POST /api/paiements/{id}/refund
	 */
	@PostMapping("/{id}/refund")
	@Operation(summary = "Annuler un paiement", description = "Api qui permet d'annuler un paiement")
			@ApiResponse(responseCode = "204", description = "paiement annulé")
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<Void> remboursement( @PathVariable Long id) {
		return ResponseEntity.noContent().build();
	}

}
