package com.chatue.bookverse.bookverse_api.service;

import java.util.List;

import com.chatue.bookverse.bookverse_api.dto.AuteurDto;
import com.chatue.bookverse.bookverse_api.dto.LivreCompletDto;
import com.chatue.bookverse.bookverse_api.dto.request.AuteurRequest;
import com.chatue.bookverse.bookverse_api.entity.Auteur;



public interface AuteurService {

	List<AuteurDto> getAllAuteurs();
	
	AuteurDto getAuteurById(Long id);
	
	AuteurDto getAuteurByNomPrenom(String nom , String prenom );
	
	List<LivreCompletDto> getLivresByAuteur(Auteur auteur);
	
	void savedAuteur(AuteurRequest auteur);
	
	void updateAuteur(Long id,AuteurRequest auteur);
	
	int deleteAuteur(Long id);
}
