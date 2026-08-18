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
import com.chatue.bookverse.bookverse_api.dto.CategorieDto;
import com.chatue.bookverse.bookverse_api.dto.request.CategorieRequest;
import com.chatue.bookverse.bookverse_api.exception.ErrorResponse;
import com.chatue.bookverse.bookverse_api.service.CategorieService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path="/v1/categories")
@Tag(name = "Categorie", description = "Api des categories")
@RequiredArgsConstructor
public class CategorieController {

	private final CategorieService categorieService;
	
	@GetMapping("/{id}")
	@Operation(summary = "Rechercher une categorie par id", description = "Api qui permet de lister une catégorie filtrée par id  ")
			@ApiResponse(responseCode = "200", description = "Liste retournée contenant tous les auteurs", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategorieDto.class)))
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<CategorieDto> trouverUneCategorieParId(@Valid @PathVariable Long id) {
		return ResponseEntity.status(200).body(categorieService.getCategorieById(id));
		
	}
	
	
	@GetMapping
	@Operation(summary = "Lister categories", description = "Api qui permet de lister toutes les categories ")
			@ApiResponse(responseCode = "200", description = "Liste retournée contenant tous les auteurs", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategorieDto.class)))
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public  ResponseEntity<List<CategorieDto>> trouverToutesLesCategories() {
		return ResponseEntity.status(200).body(categorieService.getAllCategories());
		
	}
	@DeleteMapping("/{id}")
	@Operation(summary = "Supprimer une categorie", description = "Api qui permet de supprimer une categorie ")
			@ApiResponse(responseCode = "204", description = "Opération réussie")
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<Void> supprimerCategorie(@PathVariable Long id){
		categorieService.dltCategorieById(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		
	}

	@PatchMapping("/{id}")
	@Operation(summary = "Modifier categorie", description = "Api qui permet de modifier de façon partielle le nom d'une categorie ")
			@ApiResponse(responseCode = "200", description = "Opération réussie",content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategorieDto.class)))
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<CategorieDto> modifierCategorie(@PathVariable Long id ,@Valid @RequestBody CategorieRequest categorieRequest){
		return ResponseEntity.status(200).body(categorieService. updtCategorieByNom(id,categorieRequest));
	}
	
	@PostMapping
	@Operation(summary = "Enregister une Categorie", description = "Api qui permet d'enregistrer une Categorie "
	, requestBody =@io.swagger.v3.oas.annotations.parameters.RequestBody(required=true , content=@Content(mediaType="application/json" ,schema =@Schema(implementation = CategorieRequest.class) )))
			@ApiResponse(responseCode = "200", description = "Enregistrement réussi", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategorieDto.class)))
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<CategorieDto> enregistrerCategorie(@Valid @RequestBody CategorieRequest categorie) {
		return ResponseEntity.status(HttpStatus.CREATED).body(categorieService.savedCategorie(categorie));	
	}
}
