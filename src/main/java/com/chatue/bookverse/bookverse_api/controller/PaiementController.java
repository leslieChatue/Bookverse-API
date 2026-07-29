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
import com.chatue.bookverse.bookverse_api.dto.LivreCompletDto;
import com.chatue.bookverse.bookverse_api.dto.PaiementResponseDTO;
import com.chatue.bookverse.bookverse_api.dto.PanierResponseDTO;
import com.chatue.bookverse.bookverse_api.dto.request.PaiementRequest;
import com.chatue.bookverse.bookverse_api.dto.request.PanierRequest;
import com.chatue.bookverse.bookverse_api.service.PaiementService;
import com.chatue.bookverse.bookverse_api.service.PanierService;
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
@RequestMapping(path = "/v1/paiements")
@Tag(name = "Paiments", description = "Api des Paiement")
public class PaiementController {

	private final PaiementService paiementService;


	@GetMapping("/{id}")
	@Operation(summary = "Recherche par id", description = "Api qui permet de trouver un paiement trié par id")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Paiement trouvé", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaiementResponseDTO.class))),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public ResponseEntity<BodyResponse> trouverUnPaiement(@Valid @RequestParam(required = true) Long id) {
		BodyResponse bodyResponse = new BodyResponse();
		bodyResponse.getBody().put("data", paiementService.getPaiementById(id));
		return ResponseEntity.status(200).body(bodyResponse);
	}

	@PostMapping
	@Operation(summary = "Enregister un paiement", description = "Api qui permet d'enregistrer un paiement ", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaiementRequest.class))))
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Enregistrement réussi"),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public ResponseEntity<String> EnregistrerLivre(@Valid @RequestBody PaiementRequest paiementRequest) {
		paiementService.savePaiement(paiementRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body("Paiement enregistrée avec success");
	}

	/*
	 * Lister
	GET /api/paiements
	 **/
	@GetMapping
	@Operation(summary = "Recherche de tous les paiements", description = "Api qui permet de lister tous les paiements ")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Liste retournée contenant tous les paiements", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaiementResponseDTO.class))),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public ResponseEntity<BodyResponse> trouverToutesLesCommandes(@Valid @RequestParam(required = true) Long id) {
		BodyResponse bodyResponse = new BodyResponse();
		bodyResponse.getBody().put("data", paiementService.getAllPaiements());
		return ResponseEntity.status(200).body(bodyResponse);

	}
	/*
	Remboursement (plus tard)
	POST /api/paiements/{id}/refund
	 */
	@PostMapping("/{id}/refund")
	@Operation(summary = "Annuler un paiement", description = "Api qui permet d'annuler un paiement")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "paiement annulé", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaiementResponseDTO.class))),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public ResponseEntity<BodyResponse> remboursement(@Valid @RequestParam(required = true) Long id) {
		paiementService.creerRemboursement(id);
		BodyResponse bodyResponse = new BodyResponse();
		bodyResponse.getBody().put("message","Remboursement effectué");
		return ResponseEntity.status(200).body(bodyResponse);

	}
	
	
	
	

}
