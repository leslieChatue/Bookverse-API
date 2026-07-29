package com.chatue.bookverse.bookverse_api.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.chatue.bookverse.bookverse_api.entity.Auteur;
import com.chatue.bookverse.bookverse_api.entity.Livre;

@Repository
public interface AuteurDao {

	List<Auteur> findAllAuteurs();
	
	Auteur findAuteurById(Long id);
	
	List<Livre> findLivresByAuteur(Auteur auteur);

	Auteur findAuteurByNomPrenom(String nom, String prenom);
	
	void savedAuteur(Auteur auteur);
	
	int deleteAuteur(Long id);
	
}
