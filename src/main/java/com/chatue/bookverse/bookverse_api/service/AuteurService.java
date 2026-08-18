package com.chatue.bookverse.bookverse_api.service;

import java.util.List;

import com.chatue.bookverse.bookverse_api.dto.AuteurDto;
import com.chatue.bookverse.bookverse_api.dto.LivreCompletDto;
import com.chatue.bookverse.bookverse_api.dto.request.AuteurLivreRequest;
import com.chatue.bookverse.bookverse_api.dto.request.AuteurRequest;




public interface AuteurService {

	List<AuteurDto> getAllAuteurs();
	
	AuteurDto getAuteurById(Long id);
	
	AuteurDto getAuteurByNomPrenom(String nom , String prenom );
	
	List<LivreCompletDto> getLivresByAuteur(AuteurLivreRequest auteur);
	
	AuteurDto savedAuteur(AuteurRequest auteur);
	
	AuteurDto updateAuteur(Long id,AuteurRequest auteur);
	
	void deleteAuteur(Long id);
}
