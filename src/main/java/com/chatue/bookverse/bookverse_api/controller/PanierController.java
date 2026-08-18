package com.chatue.bookverse.bookverse_api.controller;

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

import com.chatue.bookverse.bookverse_api.dto.PanierResponseDTO;
import com.chatue.bookverse.bookverse_api.dto.request.DeleteLivrePanierRequest;
import com.chatue.bookverse.bookverse_api.dto.request.PanierModifierQuantite;
import com.chatue.bookverse.bookverse_api.dto.request.PanierRequest;
import com.chatue.bookverse.bookverse_api.exception.ErrorResponse;
import com.chatue.bookverse.bookverse_api.service.PanierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
			@ApiResponse(responseCode = "200", description = "Liste retournée contenant tous les livres", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PanierResponseDTO.class)))
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<PanierResponseDTO> trouverUnPanierParId(@PathVariable Long id) {
		return ResponseEntity.status(200).body(panierService.getPanierById(id));
	}

	@PostMapping
	@Operation(summary = "Enregister un panier", description = "Api qui permet d'enregistrer un panier ", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = PanierRequest.class))))
			@ApiResponse(responseCode = "200", description = "Enregistrement réussi", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PanierResponseDTO.class)))
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<PanierResponseDTO> enregistrerPanier(@Valid @RequestBody PanierRequest panierRequest) {
		return ResponseEntity.status(HttpStatus.CREATED).body(panierService.savePanier(panierRequest.getUserId()));

	}

	/**
	 * Livres par auteur GET /api/livres/auteurs/{id}
	 */
	@PostMapping("/{id}/livres")
	@Operation(summary = "Enregistrer un livre au panier", description = "Api qui permet d'enregistrer un livre au panier", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = PanierRequest.class))))
			@ApiResponse(responseCode = "201", description = "Enregistrement réussi", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PanierResponseDTO.class)))
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<PanierResponseDTO> enregistrerLivreAuPanier(@Valid PanierRequest panierRequest) {
		    return ResponseEntity
		            .status(HttpStatus.CREATED)
		            .body(panierService.saveLivreInPanier(panierRequest));
	}
	
	/**
	 * 
	 * Modifier uniquement la quantité PATCH /api/livres/{id}/stock
	 */
	@PatchMapping("/livres/quantite")
	@Operation(summary = "Modifier la quantité d'article un livre partiellement", description = "Api qui permet de modifier uniquement la quantité d'un livre ", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Integer.class))))
			@ApiResponse(responseCode = "200", description = "Modification réussie", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PanierResponseDTO.class)))
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<PanierResponseDTO> modifierStockLivre(@Valid @RequestBody PanierModifierQuantite panierModifierQuantite) {
		return ResponseEntity.status(200).body(panierService.updateQuantite(panierModifierQuantite));
	}

	/**
	 * Supprimer DELETE /api/livres/{id}
	 */
	@DeleteMapping("/{id}/livres/{livreId}")
	@Operation(summary = "Supprimer un livre ", description = "Api qui permet de supprimier un livre d'un panier ", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeleteLivrePanierRequest.class))))
			@ApiResponse(responseCode = "204", description = "Suppréssion réussie")
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<Void> supprimerLivre( @PathVariable Long id ,@Valid @RequestBody DeleteLivrePanierRequest deleteLivrePanierRequest) {
		panierService.deleteLivrePanier(id , deleteLivrePanierRequest.livreId());
		return ResponseEntity.noContent().build();
	}

	
	/**
	 * Supprimer DELETE /api/livres/{id}
	 */
	@DeleteMapping("/{id}/clear")
	@Operation(summary = "Vider le panier ", description = "Api qui permet de vider un panier ")
			@ApiResponse(responseCode = "204", description = "Suppréssion réussie")
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<String> ViderLivre(@PathVariable Long id) {
		panierService.viderPanier(id);
		return ResponseEntity.noContent().build();
	}

	
	

}
