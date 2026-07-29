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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chatue.bookverse.bookverse_api.dto.BodyResponse;
import com.chatue.bookverse.bookverse_api.dto.LivreCompletDto;
import com.chatue.bookverse.bookverse_api.dto.LivreResumeDto;
import com.chatue.bookverse.bookverse_api.dto.request.LivreRequest;
import com.chatue.bookverse.bookverse_api.service.LivreService;

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
@RequestMapping(path = "/v1/livres")
@Tag(name = "Livres", description = "Api des livres")
public class LivreController {

	private final LivreService livreService;
	
	@GetMapping
	@Operation(summary = "Lister livres", description = "Api qui permet de lister tous les livres ")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Liste retournée contenant tous les livres", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivreCompletDto.class))),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public ResponseEntity<BodyResponse> trouverTousLeslLivres() {
		BodyResponse bodyResponse = new BodyResponse();
		List<LivreCompletDto> listeLivre = livreService.getAllLivres();
		bodyResponse.getBody().put("data", listeLivre);
		bodyResponse.getBody().put("Taille de la liste", listeLivre.size());
		return ResponseEntity.status(200).body(bodyResponse);

	}

	@GetMapping("/{titre}")
	@Operation(summary = "Recherche par titre", description = "Api qui permet de lister tous les livres filtrés par titre du livre ")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Liste retournée contenant tous les livres", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivreResumeDto.class))),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public ResponseEntity<BodyResponse> trouverTousLeslLivresParTitre(
			@Valid @RequestParam(required = true) String titre) {
		BodyResponse bodyResponse = new BodyResponse();
		List<LivreResumeDto> listeLivre = livreService.getAllLivresByTitreContaining(titre);
		bodyResponse.getBody().put("data", listeLivre);
		bodyResponse.getBody().put("Taille de la liste", listeLivre.size());
		return ResponseEntity.status(200).body(bodyResponse);

	}

	@GetMapping("/{id}")
	@Operation(summary = "Recherche par id", description = "Api qui permet d'afficher un livre par id ")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Affichage du livre", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivreCompletDto.class))),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public ResponseEntity<BodyResponse> trouverUnLivreParId(@Valid @RequestParam(required = true) Long id) {
		BodyResponse bodyResponse = new BodyResponse();
		bodyResponse.getBody().put("data", livreService.getLivreById(id));
		return ResponseEntity.status(200).body(bodyResponse);

	}

	@PostMapping
	@Operation(summary = "Enregister un livre", description = "Api qui permet d'enregistrer un livre ", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivreRequest.class))))
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Enregistrement réussi"),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public ResponseEntity<String> EnregistrerLivre(@Valid @RequestBody LivreRequest livreRequest) {
		livreService.savedLivre(livreRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body("Enregistrement réussi");

	}

	/*
	 * Livres par catégorie GET /api/livres/categories/{id}
	 */
	@GetMapping("/categories/{id}")
	@Operation(summary = "Recherche par catégorie", description = "Api qui permet de lister tous les livres filtrés par categorie du livre ")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Liste retournée contenant tous les livres", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivreResumeDto.class))),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public ResponseEntity<BodyResponse> trouverTousLeslLivresParCategorie(
			@Valid @RequestParam(required = true) Long id) {
		BodyResponse bodyResponse = new BodyResponse();
		List<LivreResumeDto> listeLivre = livreService.getLivreByCategorieId(id);
		bodyResponse.getBody().put("data", listeLivre);
		bodyResponse.getBody().put("Taille de la liste", listeLivre.size());
		return ResponseEntity.status(200).body(bodyResponse);

	}

	@GetMapping("/categories/nom/{id}")
	@Operation(summary = "Recherche par nom catégorie", description = "Api qui permet de lister tous les livres filtrés par nom de la categorie du livre ")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Liste retournée contenant tous les livres", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivreResumeDto.class))),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public ResponseEntity<BodyResponse> trouverTousLeslLivresParNomCategorie(
			@Valid @RequestParam(required = true) String titre) {
		BodyResponse bodyResponse = new BodyResponse();
		List<LivreResumeDto> listeLivre = livreService.getLivreByCategorieContaining(titre);
		bodyResponse.getBody().put("data", listeLivre);
		bodyResponse.getBody().put("Taille de la liste", listeLivre.size());
		return ResponseEntity.status(200).body(bodyResponse);

	}

	/*
	 * Livres par auteur GET /api/livres/auteurs/{id}
	 */
	@GetMapping("/auteurs/{id}")
	@Operation(summary = "Recherche par auteur", description = "Api qui permet de lister tous les livres filtrés par auteur ")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Liste retournée contenant tous les livres", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivreResumeDto.class))),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public ResponseEntity<BodyResponse> trouverTousLeslLivresParAuteur(@Valid @RequestParam(required = true) Long id) {
		BodyResponse bodyResponse = new BodyResponse();
		List<LivreResumeDto> listeLivre = livreService.getLivreByAuteurId(id);
		bodyResponse.getBody().put("data", listeLivre);
		bodyResponse.getBody().put("Taille de la liste", listeLivre.size());
		return ResponseEntity.status(200).body(bodyResponse);

	}

	/*
	 * Modifier PUT /api/livres/{id}
	 */
	@PutMapping("/{id}")
	@Operation(summary = "Modifier un livre", description = "Api qui permet de modifier un livre ", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivreRequest.class))))
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Modication réussie"),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public ResponseEntity<String> ModifierLivre(@Valid @RequestParam(required = true) Long id,
			@Valid @RequestBody LivreRequest livreRequest) {
		livreService.updateLivre( id , livreRequest);
		return ResponseEntity.ok("Modification enregistrée avec success");
	}

	/*
	 * 
	 * Modifier uniquement le stock PATCH /api/livres/{id}/stock
	 */
	@PatchMapping("/{id}/stock")
	@Operation(summary = "Modifier un livre partiellement", description = "Api qui permet de modifieruniquement le stock d'un livre ", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Integer.class))))
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Modication réussie"),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public ResponseEntity<String> ModifierStockLivre(@Valid @RequestParam(required = true) Long id,
			@Valid @RequestBody Integer stock) {
		livreService.updateStockLivre(id , stock);
		return ResponseEntity.ok("Modification partielle enregistrée avec success");
	}

	/*
	 * Supprimer DELETE /api/livres/{id}
	 */
	@DeleteMapping("/{id}")
	@Operation(summary = "Supprimer un livre ", description = "Api qui permet de supprimer un livre ")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Modication réussie"),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public ResponseEntity<String> SupprimerLivre(@Valid @RequestParam(required = true) Long id){		
		livreService.deleteLivre(id);
		return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).body("Suppréssion réussie avec success");
	}

	// GET /api/livres?page=0&size=10 (pagination)
	@GetMapping("/livres/pages")
	@Operation(summary = "Affichage des livres par pagination", description = "Api qui permet de lister tous les livres par pagination ")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Liste retournée contenant tous les livres", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivreCompletDto.class))),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public ResponseEntity<BodyResponse> trouverLesLivresParPagination(
			Pageable pageable /* gère automatiquement page, size, sort */) {
		BodyResponse bodyResponse = new BodyResponse();
		Page<LivreCompletDto> listeLivre = livreService.getAllLivresPageable(pageable);
		bodyResponse.getBody().put("data", listeLivre);
		bodyResponse.getBody().put("Taille de la liste", listeLivre.getSize());
		return ResponseEntity.status(200).body(bodyResponse);

	}

	/*
	 * GET /api/livres?minPrix=10&maxPrix=30 GET /api/livres?stockDisponible=true
	 */
	@GetMapping("/livres/prix")
	@Operation(summary = "Recherche par prix", description = "Api qui permet de lister tous les livres filtrée par prix ")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Liste retournée contenant tous les livres", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivreCompletDto.class))),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public ResponseEntity<BodyResponse> trouverLesLivresParDisponibilites(BigDecimal minPrix, BigDecimal maxPrix,
			Boolean stockDisponible, Pageable pageable /* gère automatiquement page, size, sort */) {
		BodyResponse bodyResponse = new BodyResponse();
		Page<LivreCompletDto> listeLivre = livreService.getAllLivresByStockAndByPrix(minPrix, maxPrix, stockDisponible,
				pageable);
		bodyResponse.getBody().put("data", listeLivre);
		bodyResponse.getBody().put("Taille de la liste", listeLivre.getSize());
		return ResponseEntity.status(200).body(bodyResponse);

	}

}
