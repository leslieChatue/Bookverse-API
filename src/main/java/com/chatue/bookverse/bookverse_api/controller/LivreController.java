package com.chatue.bookverse.bookverse_api.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.chatue.bookverse.bookverse_api.dto.LivreCompletDto;
import com.chatue.bookverse.bookverse_api.dto.LivreResumeDto;
import com.chatue.bookverse.bookverse_api.dto.request.LivreRequest;
import com.chatue.bookverse.bookverse_api.dto.request.UpdateStockLivreRequest;
import com.chatue.bookverse.bookverse_api.exception.ErrorResponse;
import com.chatue.bookverse.bookverse_api.service.LivreService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/livres")
@Tag(name = "Livres", description = "Api des livres")
public class LivreController {

	private final LivreService livreService;

	@GetMapping
	@Operation(summary = "Lister livres", description = "Api qui permet de lister tous les livres ")
	@ApiResponse(responseCode = "200", description = "Liste retournée contenant tous les livres", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivreCompletDto.class)))
	@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	@ApiResponse(responseCode = "500", description = "Erreur interne serveur", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<List<LivreCompletDto>> trouverTousLeslLivres() {
		return ResponseEntity.status(200).body(livreService.getAllLivres());
	}

	@GetMapping("/{titre}")
	@Operation(summary = "Recherche par titre", description = "Api qui permet de lister tous les livres filtrés par titre du livre ")
	@ApiResponse(responseCode = "200", description = "Liste retournée contenant tous les livres", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivreResumeDto.class)))
	@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	@ApiResponse(responseCode = "500", description = "Erreur interne serveur", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<List<LivreResumeDto>> trouverTousLeslLivresParTitre(@PathVariable String titre) {
		return ResponseEntity.status(200).body(livreService.getAllLivresByTitreContaining(titre));
	}

	@GetMapping("/{id}")
	@Operation(summary = "Recherche par id", description = "Api qui permet d'afficher un livre par id ")
	@ApiResponse(responseCode = "200", description = "Affichage du livre", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivreCompletDto.class)))
	@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	@ApiResponse(responseCode = "500", description = "Erreur interne serveur", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<LivreCompletDto> trouverUnLivreParId(@PathVariable Long id) {
		return ResponseEntity.status(200).body(livreService.getLivreById(id));

	}

	@PostMapping
	@Operation(summary = "Enregister un livre", description = "Api qui permet d'enregistrer un livre ", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivreRequest.class))))
	@ApiResponse(responseCode = "200", description = "Enregistrement réussi",content=@Content(mediaType = "application/json", schema = @Schema(implementation = LivreResumeDto.class)))
	@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	@ApiResponse(responseCode = "500", description = "Erreur interne serveur", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<LivreResumeDto> enregistrerLivre(@Valid @RequestBody LivreRequest livreRequest) {
		return ResponseEntity.status(HttpStatus.CREATED).body(livreService.savedLivre(livreRequest));

	}

	/**
	 * Livres par catégorie GET /api/livres/categories/{id}
	 */
	@GetMapping("/categories/{id}")
	@Operation(summary = "Recherche par catégorie", description = "Api qui permet de lister tous les livres filtrés par categorie du livre ")
	@ApiResponse(responseCode = "200", description = "Liste retournée contenant tous les livres", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivreResumeDto.class)))
	@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	@ApiResponse(responseCode = "500", description = "Erreur interne serveur", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<List<LivreResumeDto>> trouverTousLeslLivresParCategorie(@PathVariable Long id) {
		return ResponseEntity.status(200).body(livreService.getLivreByCategorieId(id));

	}

	@GetMapping("/categories/nom/{titre}")
	@Operation(summary = "Recherche par nom catégorie", description = "Api qui permet de lister tous les livres filtrés par nom de la categorie du livre ")
	@ApiResponse(responseCode = "200", description = "Liste retournée contenant tous les livres", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivreResumeDto.class)))
	@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	@ApiResponse(responseCode = "500", description = "Erreur interne serveur", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<List<LivreResumeDto>> trouverTousLeslLivresParNomCategorie(@PathVariable String titre) {
		return ResponseEntity.status(200).body(livreService.getLivreByCategorieContaining(titre));
	}

	/**
	 * Livres par auteur GET /api/livres/auteurs/{id}
	 */
	@GetMapping("/auteurs/{id}")
	@Operation(summary = "Recherche par auteur", description = "Api qui permet de lister tous les livres filtrés par auteur ")
	@ApiResponse(responseCode = "200", description = "Liste retournée contenant tous les livres", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivreResumeDto.class)))
	@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	@ApiResponse(responseCode = "500", description = "Erreur interne serveur", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<List<LivreResumeDto>> trouverTousLeslLivresParAuteur(@PathVariable Long id) {
		return ResponseEntity.status(200).body(livreService.getLivreByAuteurId(id));
	}

	/**
	 * Modifier PUT /api/livres/{id}
	 */
	@PutMapping("/{id}")
	@Operation(summary = "Modifier un livre", description = "Api qui permet de modifier un livre ", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivreRequest.class))))
	@ApiResponse(responseCode = "200", description = "Modification réussie", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivreResumeDto.class)))
	@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	@ApiResponse(responseCode = "500", description = "Erreur interne serveur", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<LivreResumeDto> modifierLivre(@PathVariable Long id,
			@Valid @RequestBody LivreRequest livreRequest) {
		return ResponseEntity.status(200).body(livreService.updateLivre(id, livreRequest));
	}

	/**
	 * 
	 * Modifier uniquement le stock PATCH /api/livres/{id}/stock
	 */
	@PatchMapping("/{id}/stock")
	@Operation(summary = "Modifier un livre partiellement", description = "Api qui permet de modifieruniquement le stock d'un livre ", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = UpdateStockLivreRequest.class))))
	@ApiResponse(responseCode = "200", description = "Modication réussie")
	@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	@ApiResponse(responseCode = "500", description = "Erreur interne serveur", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<LivreResumeDto> modifierStockLivre(@PathVariable Long id, @Valid @RequestBody UpdateStockLivreRequest stock) {
		return ResponseEntity.status(200).body(livreService.updateStockLivre(id, stock));
	}

	/**
	 * Supprimer DELETE /api/livres/{id}
	 */
	@DeleteMapping("/{id}")
	@Operation(summary = "Supprimer un livre ", description = "Api qui permet de supprimer un livre ")
	@ApiResponse(responseCode = "200", description = "Modification réussie")
	@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	@ApiResponse(responseCode = "500", description = "Erreur interne serveur", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<Void> supprimerLivre(@PathVariable Long id) {
		livreService.deleteLivre(id);
		return ResponseEntity.noContent().build();
	}

	// GET /api/livres?page=0&size=10 (pagination)
	@GetMapping("/livres/pages")
	@Operation(summary = "Affichage des livres par pagination", description = "Api qui permet de lister tous les livres par pagination ")
	@ApiResponse(responseCode = "200", description = "Liste retournée contenant tous les livres", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivreCompletDto.class)))
	@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	@ApiResponse(responseCode = "500", description = "Erreur interne serveur", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<Page<LivreCompletDto>> trouverLesLivresParPagination(
			Pageable pageable /* gère automatiquement page, size, sort */) {
		return ResponseEntity.status(200).body(livreService.getAllLivresPageable(pageable));

	}

	/**
	 * GET /api/livres?minPrix=10&maxPrix=30 GET /api/livres?stockDisponible=true
	 */
	@GetMapping("/livres/prix")
	@Operation(summary = "Recherche par prix", description = "Api qui permet de lister tous les livres filtrée par prix ")
	@ApiResponse(responseCode = "200", description = "Liste retournée contenant tous les livres", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivreCompletDto.class)))
	@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	@ApiResponse(responseCode = "500", description = "Erreur interne serveur", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<Page<LivreCompletDto>> trouverLesLivresParDisponibilites(BigDecimal minPrix,
			BigDecimal maxPrix, Boolean stockDisponible,
			Pageable pageable /* gère automatiquement page, size, sort */) {
		return ResponseEntity.status(200)
				.body(livreService.getAllLivresByStockAndByPrix(minPrix, maxPrix, stockDisponible, pageable));

	}

}
