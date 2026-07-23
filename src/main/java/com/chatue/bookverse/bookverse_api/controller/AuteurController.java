package com.chatue.bookverse.bookverse_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.chatue.bookverse.bookverse_api.dto.AuteurDto;
import com.chatue.bookverse.bookverse_api.dto.BodyResponse;
import com.chatue.bookverse.bookverse_api.dto.LivreCompletDto;
import com.chatue.bookverse.bookverse_api.dto.LivreResumeDto;
import com.chatue.bookverse.bookverse_api.dto.request.AuteurRequest;
import com.chatue.bookverse.bookverse_api.dto.request.RechercherAuteurNomRequest;
import com.chatue.bookverse.bookverse_api.entity.Auteur;
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

/**
 * Controller de la classe Auteur
 */

/**
 * 
 */
@RestController
@RequestMapping(path="/v1")
@Tag(name = "Auteur", description = "Api des auteurs")
public class AuteurController {

	
	private final AuteurService auteurService;
	private final AuteurMapper auteurMapper;
	/**
	 * @param auteurService
	 * @param auteurMapper
	 */
	public AuteurController(AuteurService auteurService, LivreMapper livreMapper,AuteurMapper auteurMapper) {
		super();
		this.auteurService = auteurService;
		this.auteurMapper=auteurMapper;
	}
	
	
	

	/**
	 * @return
	 */
	@GetMapping("/Auteurs")
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

	@PostMapping("/Auteurs/Livres")
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

	@PostMapping("/Auteurs/EnregisterAuteur")
	@Operation(summary = "Enregister un auteur", description = "Api qui permet d'enregistrer un auteur "
	, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required=true , content=@Content(mediaType="application/json" ,schema =@Schema(implementation = AuteurRequest.class) )))
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Enregistrement réussi"),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public ResponseEntity<String> EnregistrerAuteur(@Valid @RequestBody AuteurRequest auteur) {
		Auteur aut= new Auteur();
		aut.setBiographie(auteur.getBiographie());
		aut.setNom(auteur.getNom());
		aut.setPrenom(auteur.getPrenom());
		System.out.println("---->>>>>>>>"+auteur.toString());
		auteurService.savedAuteur(aut);
		return ResponseEntity.ok("Auteur enregistrée avec success");
		
	}


	@GetMapping("/Auteurs/Id/{id}")
	@Operation(summary = "Rechercher un auteur par id", description = "Api qui permet de rechercher un auteur filtré par id")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Liste retournée contenant tous les auteurs", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuteurDto.class))),
			@ApiResponse(responseCode = "404", description = "Aucune ressource trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur interne serveur") })
	public ResponseEntity<BodyResponse> trouverUnAuteurParId(@Valid @PathVariable Long id) {
		BodyResponse bodyResponse = new BodyResponse();
		bodyResponse.getBody().put("data",auteurService.getAuteurById(id));
		return ResponseEntity.status(200).body(bodyResponse);
		
	}

}
