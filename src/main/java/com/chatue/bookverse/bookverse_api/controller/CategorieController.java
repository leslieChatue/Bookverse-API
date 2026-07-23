package com.chatue.bookverse.bookverse_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatue.bookverse.bookverse_api.dto.BodyResponse;
import com.chatue.bookverse.bookverse_api.dto.CategorieDto;
import com.chatue.bookverse.bookverse_api.dto.request.CategorieRequest;
import com.chatue.bookverse.bookverse_api.entity.Categorie;
import com.chatue.bookverse.bookverse_api.mapper.CategorieMapper;
import com.chatue.bookverse.bookverse_api.service.CategorieService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path="/v1/Categories")
@Tag(name = "Categorie", description = "Api des categories")
public class CategorieController {

	private final CategorieService categorieService;
	public CategorieController(CategorieService categorieService, CategorieMapper categorieMapper) {
		super();
		this.categorieService = categorieService;
	}



	@GetMapping("/Id/{id}")
	@Operation(summary = "Rechercher une categorie par id", description = "Api qui permet de lister une catégorie filtrée par id  ")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Liste retournée contenant tous les auteurs", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategorieDto.class))),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public ResponseEntity<BodyResponse> trouverUneCategorieParId(@Valid @PathVariable Long id) {
		BodyResponse bodyResponse = new BodyResponse();
		bodyResponse.getBody().put("data", categorieService.getCategorieById(id));
		return ResponseEntity.status(200).body(bodyResponse);
		
	}
	
	
	@GetMapping("/ListeCategories")
	@Operation(summary = "Lister categories", description = "Api qui permet de lister toutes les categories ")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Liste retournée contenant tous les auteurs", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategorieDto.class))),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public  ResponseEntity<BodyResponse> trouverToutesLesCategories() {
		BodyResponse bodyResponse = new BodyResponse();
		List<CategorieDto> listeCategorie=categorieService.getAllCategories();
		bodyResponse.getBody().put("data",listeCategorie );
		bodyResponse.getBody().put("Taille de la liste", listeCategorie.size());
		return ResponseEntity.status(200).body(bodyResponse);
		
	}
	@DeleteMapping("/RetirerCategorie/{id}")
	@Operation(summary = "Supprimer une categorie", description = "Api qui permet de supprimer une categorie ")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Opération réussie", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public ResponseEntity<Integer> SupprimerCategorie(@PathVariable @Valid Long id){
		Integer nbre =categorieService.dltCategorieById(id);
		return ResponseEntity.status(200).body(nbre);
		
	}

	@PutMapping("/ModiferCategorie/{id}/{nouveauNom}")
	@Operation(summary = "Modifier categorie", description = "Api qui permet de modifier le nom d'une categorie ")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Opération réussie",content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategorieDto.class))),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public ResponseEntity<Integer> ModifierrCategorie(@PathVariable @Valid Long id , String nouveauNom){
		
		return ResponseEntity.status(200).body(categorieService. updtCategorieByNom(nouveauNom,  id));
		
	}
	
	@PostMapping("/EnregisterCategorie")
	@Operation(summary = "Enregister une Categorie", description = "Api qui permet d'enregistrer une Categorie "
	, requestBody =@io.swagger.v3.oas.annotations.parameters.RequestBody(required=true , content=@Content(mediaType="application/json" ,schema =@Schema(implementation = CategorieRequest.class) )))
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Enregistrement réussi"),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public ResponseEntity<String> EnregistrerAuteur(@Valid @RequestBody CategorieRequest categorie) {
		Categorie cat= new Categorie();
		cat.setNom(categorie.getNom());
		categorieService.savedCategorie(cat);
		return ResponseEntity.ok("Auteur enregistrée avec success");
		
	}
}
