package com.chatue.bookverse.bookverse_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.chatue.bookverse.bookverse_api.dto.AuteurDto;
import com.chatue.bookverse.bookverse_api.dto.LivreCompletDto;
import com.chatue.bookverse.bookverse_api.dto.request.AuteurLivreRequest;
import com.chatue.bookverse.bookverse_api.dto.request.AuteurRequest;
import com.chatue.bookverse.bookverse_api.dto.request.RechercherAuteurNomRequest;
import com.chatue.bookverse.bookverse_api.exception.ErrorResponse;
import com.chatue.bookverse.bookverse_api.service.AuteurService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controller de la classe Auteur
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path="/v1")
@Tag(name = "Auteur", description = "Api des auteurs")
public class AuteurController {

	private final AuteurService auteurService;
	
	
	@GetMapping("/auteurs")
	@Operation(summary = "Lister auteurs", description = "Api qui permet de lister tous les auteurs ")
			@ApiResponse(responseCode = "200", description = "Liste retournée contenant tous les auteurs", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuteurDto.class)))
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée",content=@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur",content=@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) 
	public  ResponseEntity<List<AuteurDto>> trouverTousLeslAuteurs() {
			return ResponseEntity.status(HttpStatus.FOUND).body(auteurService.getAllAuteurs());	
	}

	@PostMapping("/auteurs/livres")
	@Operation(summary = "Recherche livre d'un auteur par le  titre", description = "Api qui permet de lister tous les livres d'un auteur en particulier filtré par le titre du livre "
	, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required=true , content=@Content(mediaType="application/json" ,schema =@Schema(implementation = RechercherAuteurNomRequest.class) )))
			@ApiResponse(responseCode = "200", description = "Liste retournée contenant tous les livres", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivreCompletDto.class)))
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée",content=@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur",content=@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<List<LivreCompletDto>> trouverTousLesLivresParTitre(@Valid @RequestBody AuteurLivreRequest auteur) {
		return ResponseEntity.status(200).body(auteurService.getLivresByAuteur(auteur));	
	}

	@PostMapping("/auteurs/enregisterAuteur")
	@Operation(summary = "Enregister un auteur", description = "Api qui permet d'enregistrer un auteur "
	, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required=true , content=@Content(mediaType="application/json" ,schema =@Schema(implementation = AuteurRequest.class) )))
			@ApiResponse(responseCode = "200", description = "Enregistrement réussi",content=@Content(mediaType = "application/json", schema = @Schema(implementation = AuteurDto.class)))
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée",content=@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			@ApiResponse(responseCode = "409", description = "Un auteur est déjà présent avec ces informations",content=@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))	
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur",content=@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) 
	public ResponseEntity<AuteurDto> enregistrerAuteur(@Valid @RequestBody AuteurRequest auteurRequest) {
		return ResponseEntity.status(HttpStatus.CREATED).body(auteurService.savedAuteur(auteurRequest));
		
	}


	@GetMapping("/auteurs/{id}")
	@Operation(summary = "Rechercher un auteur par id", description = "Api qui permet de rechercher un auteur filtré par id")
			@ApiResponse(responseCode = "200", description = "Liste retournée contenant tous les auteurs", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuteurDto.class)))
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée",content=@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur",content=@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<AuteurDto> trouverUnAuteurParId(@PathVariable Long id) {
		return ResponseEntity.status(200).body(auteurService.getAuteurById(id));
		
	}
	
	//Modifier toute l'entite auteur
	@PutMapping("/auteurs/{id}")
	@Operation(summary = "Modifier un auteur", description = "Api qui permet de modifier un auteur "
	, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required=true , content=@Content(mediaType="application/json" ,schema =@Schema(implementation = AuteurRequest.class) )))
			@ApiResponse(responseCode = "200", description = "Modication réussie",content=@Content(mediaType = "application/json", schema = @Schema(implementation = AuteurDto.class)))
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée",content=@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur",content=@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<AuteurDto> modifierAuteur(@PathVariable Long id , @Valid AuteurRequest auteurRequest) {
		return ResponseEntity.status(HttpStatus.OK).body(auteurService.savedAuteur(auteurRequest));
	}


	@DeleteMapping("/auteurs/{id}")
	@Operation(summary = "Supprimer un auteur par id", description = "Api qui permet de supprimer un auteur filtré par id")
			@ApiResponse(responseCode = "204", description = "Liste retournée contenant tous les auteurs")
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée",content=@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur",content=@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) 
	public ResponseEntity<Void> supprimererUnAuteurParId( @PathVariable Long id) {
		auteurService.deleteAuteur(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		
	}
}
