package com.chatue.bookverse.bookverse_api.controller;

import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatue.bookverse.bookverse_api.dao.AuteurDao;
import com.chatue.bookverse.bookverse_api.dao.CategorieDao;
import com.chatue.bookverse.bookverse_api.dto.BodyResponse;
import com.chatue.bookverse.bookverse_api.dto.LivreCompletDto;
import com.chatue.bookverse.bookverse_api.dto.LivreResumeDto;
import com.chatue.bookverse.bookverse_api.dto.request.LivreRequest;
import com.chatue.bookverse.bookverse_api.entity.Livre;
import com.chatue.bookverse.bookverse_api.service.LivreService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/v1")
@Tag(name = "Livres", description = "Api des livres")
public class LivreController {

	private final LivreService livreService;
	private final CategorieDao categorieDao;
	private final AuteurDao auteurDao;
	public LivreController(LivreService livreService,CategorieDao categorieDao,AuteurDao auteurDao) {
		super();
		this.livreService = livreService;
		this.auteurDao=auteurDao;
		this.categorieDao=categorieDao;
	}

	@GetMapping("/Livres")
	@Operation(summary = "Lister livres", description = "Api qui permet de lister tous les livres ")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Liste retournée contenant tous les livres", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivreResumeDto.class))),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public  ResponseEntity<BodyResponse> trouverTousLeslLivres() {
		BodyResponse bodyResponse = new BodyResponse();
		List<LivreCompletDto> listeLivre=livreService.getAllLivres();
		bodyResponse.getBody().put("data",listeLivre );
		bodyResponse.getBody().put("Taille de la liste", listeLivre.size());
		return ResponseEntity.status(200).body(bodyResponse);
		
	}

	@GetMapping("/Livres/Titre/{titre}")
	@Operation(summary = "Recherche par titre", description = "Api qui permet de lister tous les livres filtrée par titre du livre ")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Liste retournée contenant tous les livres", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivreCompletDto.class))),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public ResponseEntity<BodyResponse> trouverTousLeslLivresParTitre(@Valid @PathVariable String titre) {
		BodyResponse bodyResponse = new BodyResponse();
		List<LivreResumeDto> listeLivre=livreService.getAllLivresByTitreContaining(titre);
		bodyResponse.getBody().put("data",listeLivre );
		bodyResponse.getBody().put("Taille de la liste", listeLivre.size());
		return ResponseEntity.status(200).body(bodyResponse);
		
	}

	@GetMapping("/Livres/Id/{id}")
	@Operation(summary = "Recherche par id", description = "Api qui permet de lister tous les livres filtrée par id du livre ")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Liste retournée contenant tous les livres", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivreCompletDto.class))),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public ResponseEntity<BodyResponse> trouverUnLivreParId(@Valid @PathVariable Long id) {
		BodyResponse bodyResponse = new BodyResponse();
		bodyResponse.getBody().put("data", livreService.getLivreById(id));
		return ResponseEntity.status(200).body(bodyResponse);
		
	}

	
	@PostMapping("/EnregisterLivre")
	@Operation(summary = "Enregister un livre", description = "Api qui permet d'enregistrer un livre "
	, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required=true , content=@Content(mediaType="application/json" ,schema =@Schema(implementation = LivreRequest.class) )))
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Enregistrement réussi"),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public ResponseEntity<String> EnregistrerAuteur(@Valid @RequestBody LivreRequest livreRequest) {
		Livre livre= new Livre();
		livre.setAuteur(auteurDao.findAuteurById(livreRequest.getAuteurId()));
		livre.setCategorie(categorieDao.findCategorieById(livreRequest.getCategorieId()));
		livre.setDateCreation(new Date());
		livre.setDescription(livreRequest.getDescription());
		livre.setIsbn(livreRequest.getIsbn());
		livre.setPrix(livreRequest.getPrix());
		livre.setStock(livreRequest.getStock());
		livre.setTitre(livreRequest.getTitre());
		livreService.savedLivre(livre);
		return ResponseEntity.ok("Livre enregistrée avec success");
		
	}
}
