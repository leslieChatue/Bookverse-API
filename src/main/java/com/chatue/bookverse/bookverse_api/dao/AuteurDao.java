package com.chatue.bookverse.bookverse_api.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.chatue.bookverse.bookverse_api.entity.Auteur;
import com.chatue.bookverse.bookverse_api.entity.Livre;

@Repository
public interface AuteurDao   {
	
	List<Auteur> findAllAuteurs();
	
	Optional<Auteur> findAuteurById(Long id);
	
	List<Livre> findLivresByAuteur(Auteur auteur);
	
	Optional<Auteur> findAuteurByNomPrenom(String nom, String prenom);
	
	void savedAuteur(Auteur auteur);
	
	void deleteAuteur(Long id);
	
	boolean existsAuteurByNomPrenom(String nom, String prenom);

	
}
