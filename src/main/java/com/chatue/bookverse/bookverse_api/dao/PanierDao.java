package com.chatue.bookverse.bookverse_api.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatue.bookverse.bookverse_api.entity.Panier;

public interface PanierDao extends JpaRepository<Panier, Long> {

	Optional<Panier> findByUtilisateurId(Long utilisateurId);
	
	boolean existsByUtilisateurId(Long utilisateurId);
	
	
}
