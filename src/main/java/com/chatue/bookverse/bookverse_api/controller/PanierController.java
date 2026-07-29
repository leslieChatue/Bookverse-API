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
import com.chatue.bookverse.bookverse_api.dto.PanierResponseDTO;
import com.chatue.bookverse.bookverse_api.dto.request.PanierModifierQuantite;
import com.chatue.bookverse.bookverse_api.dto.request.PanierRequest;
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
@RequestMapping(path = "/v1/paniers")
@Tag(name = "Paniers", description = "Api des Paniers")
public class PanierController {

	private final PanierService panierService;


	@GetMapping("/{id}")
	@Operation(summary = "Recherche par id", description = "Api qui permet de lister tous les livres filtrée par id du livre ")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Liste retournée contenant tous les livres", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PanierResponseDTO.class))),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public ResponseEntity<BodyResponse> trouverUnPanierParId(@Valid @RequestParam(required = true) Long id) {
		BodyResponse bodyResponse = new BodyResponse();
		bodyResponse.getBody().put("data", panierService.getPanierById(id));
		return ResponseEntity.status(200).body(bodyResponse);

	}

	@PostMapping
	@Operation(summary = "Enregister un panier", description = "Api qui permet d'enregistrer un panier ", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = PanierRequest.class))))
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Enregistrement réussi"),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public ResponseEntity<String> EnregistrerPanier(@Valid @RequestBody PanierRequest panierRequest) {
		panierService.savePanier(panierRequest.getUserId());
		return ResponseEntity.status(HttpStatus.CREATED).body("Panier enregistrée avec success");

	}

	/*
	 * Livres par auteur GET /api/livres/auteurs/{id}
	 */
	@PostMapping("/{id}/livres")
	@Operation(summary = "Enregistrer un livre au panier", description = "Api qui permet d'enregistrer un livre au panier", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = PanierRequest.class))))
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "Enregistrement réussi", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PanierResponseDTO.class))),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public ResponseEntity<BodyResponse> enregistrerLivreAuPanier(@Valid PanierRequest panierRequest) {
		
		  PanierResponseDTO panier =
		            panierService.saveLivreInPanier(panierRequest);
		    BodyResponse response = new BodyResponse();
		    response.getBody().put("data", panier);
		    return ResponseEntity
		            .status(HttpStatus.CREATED)
		            .body(response);
	}
	
	/*
	 * 
	 * Modifier uniquement la quantité PATCH /api/livres/{id}/stock
	 */
	@PatchMapping("/{id}/livres/{livreId}/quantite/{quantite}")
	@Operation(summary = "Modifier la quantité d'article un livre partiellement", description = "Api qui permet de modifier uniquement la quantité d'un livre ", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Integer.class))))
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Modication réussie"),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public ResponseEntity<String> ModifierStockLivre(@Valid @RequestBody PanierModifierQuantite panierModifierQuantite) {
		panierService.updateQuantite(panierModifierQuantite);
		return ResponseEntity.ok("Modification partielle enregistrée avec success");
	}

	/*
	 * Supprimer DELETE /api/livres/{id}
	 */
	@DeleteMapping("/{id}/livres/{livreId}")
	@Operation(summary = "Supprimer un livre ", description = "Api qui permet de supprimier un livre d'un panier ", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Integer.class))))
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Suppréssion réussie"),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public ResponseEntity<String> SupprimerLivre(@Valid @RequestParam(required = true) Long id ,@RequestParam(required = true) Long livreId) {
		panierService.deleteLivrePanier(id , livreId);
		return ResponseEntity.ok("Suppréssion enregistrée avec success");
	}

	
	/*
	 * Supprimer DELETE /api/livres/{id}
	 */
	@DeleteMapping("/{id}/clear")
	@Operation(summary = "Vider le panier ", description = "Api qui permet de vider un panier ", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Integer.class))))
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Suppréssion réussie"),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public ResponseEntity<String> ViderLivre(@Valid @RequestParam(required = true) Long id) {
		panierService.viderPanier(id);
		return ResponseEntity.ok("Suppréssion enregistrée avec success");
	}

	
	

}
