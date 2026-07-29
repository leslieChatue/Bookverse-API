package com.chatue.bookverse.bookverse_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.chatue.bookverse.bookverse_api.dto.AuteurDto;
import com.chatue.bookverse.bookverse_api.dto.BodyResponse;
import com.chatue.bookverse.bookverse_api.dto.LivreCompletDto;
import com.chatue.bookverse.bookverse_api.dto.LivreResumeDto;
import com.chatue.bookverse.bookverse_api.dto.request.AuteurRequest;
import com.chatue.bookverse.bookverse_api.dto.request.RechercherAuteurNomRequest;
import com.chatue.bookverse.bookverse_api.mapper.AuteurMapper;
import com.chatue.bookverse.bookverse_api.mapper.LivreMapper;
import com.chatue.bookverse.bookverse_api.service.AuteurService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controller de la classe Auteur
 */

/**
 * 
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path="/v1")
@Tag(name = "Auteur", description = "Api des auteurs")
public class AuteurController {

	private final AuteurService auteurService;
	private final AuteurMapper auteurMapper;
	
	
	@GetMapping("/auteurs")
	@Operation(summary = "Lister auteurs", description = "Api qui permet de lister tous les auteurs ")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Liste retournée contenant tous les auteurs", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuteurDto.class))),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public  ResponseEntity<BodyResponse> trouverTousLeslAuteurs() {
		BodyResponse bodyResponse = new BodyResponse();
		List<AuteurDto> listeAuteur=auteurService.getAllAuteurs();
		bodyResponse.getBody().put("data",listeAuteur );
		bodyResponse.getBody().put("Taille de la liste", listeAuteur.size());
		return ResponseEntity.status(200).body(bodyResponse);
		
	}

	@PostMapping("/auteurs/Livres")
	@Operation(summary = "Recherche livre d'un auteur par le  titre", description = "Api qui permet de lister tous les livres d'un auteur en particulier filtré par le titre du livre "
	, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required=true , content=@Content(mediaType="application/json" ,schema =@Schema(implementation = RechercherAuteurNomRequest.class) )))
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Liste retournée contenant tous les livres", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivreResumeDto.class))),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public ResponseEntity<BodyResponse> trouverTousLeslLivresParTitre(@Valid @RequestBody RechercherAuteurNomRequest auteur) {
		AuteurDto aut = auteurService.getAuteurByNomPrenom(auteur.getNom(), auteur.getPrenom());
		BodyResponse bodyResponse = new BodyResponse();
		List<LivreCompletDto> listeAuteur=auteurService.getLivresByAuteur(auteurMapper.toEntity(aut));
		bodyResponse.getBody().put("data",listeAuteur );
		bodyResponse.getBody().put("Taille de la liste", listeAuteur.size());
		return ResponseEntity.status(200).body(bodyResponse);
		
	}

	@PostMapping("/auteurs/EnregisterAuteur")
	@Operation(summary = "Enregister un auteur", description = "Api qui permet d'enregistrer un auteur "
	, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required=true , content=@Content(mediaType="application/json" ,schema =@Schema(implementation = AuteurRequest.class) )))
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Enregistrement réussi"),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public ResponseEntity<String> EnregistrerAuteur(@Valid @RequestBody AuteurRequest auteur) {
		auteurService.savedAuteur(auteur);
		return ResponseEntity.status(HttpStatus.CREATED).body("Auteur enregistrée avec success");
		
	}


	@GetMapping("/auteurs/{id}")
	@Operation(summary = "Rechercher un auteur par id", description = "Api qui permet de rechercher un auteur filtré par id")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Liste retournée contenant tous les auteurs", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuteurDto.class))),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public ResponseEntity<BodyResponse> trouverUnAuteurParId(@Valid @RequestParam(required = true) Long id) {
		BodyResponse bodyResponse = new BodyResponse();
		bodyResponse.getBody().put("data",auteurService.getAuteurById(id));
		return ResponseEntity.status(200).body(bodyResponse);
		
	}
	
	//Modifier toute l'entite auteur
	@PutMapping("/auteurs/{id}")
	@Operation(summary = "Modifier un auteur", description = "Api qui permet de modifier un auteur "
	, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required=true , content=@Content(mediaType="application/json" ,schema =@Schema(implementation = AuteurRequest.class) )))
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Modication réussie"),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public ResponseEntity<String> ModifierAuteur(@Valid @RequestParam(required = true) Long id , @Valid AuteurRequest auteur) {
		auteurService.updateAuteur(id , auteur);
		return ResponseEntity.ok("Auteur modifié avec success");
	}


	@DeleteMapping("/auteurs/{id}")
	@Operation(summary = "Supprimer un auteur par id", description = "Api qui permet de supprimer un auteur filtré par id")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Liste retournée contenant tous les auteurs", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public ResponseEntity<BodyResponse> supprimererUnAuteurParId(@Valid @RequestParam(required = true) Long id) {
		BodyResponse bodyResponse = new BodyResponse();
		bodyResponse.getBody().put("data",auteurService.deleteAuteur(id));
		bodyResponse.getBody().put("message", "Suppréssion réussie");
		return ResponseEntity.status(200).body(bodyResponse);
		
	}
}
