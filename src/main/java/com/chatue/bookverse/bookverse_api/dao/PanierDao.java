package com.chatue.bookverse.bookverse_api.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chatue.bookverse.bookverse_api.entity.Panier;

@Repository
public interface PanierDao extends JpaRepository<Panier, Long> {

	Optional<Panier> findByUtilisateurId(Long utilisateurId);
	
	boolean existsByUtilisateurId(Long utilisateurId);
	
	Optional<Panier> findById(Long panierId);

	void saveLivreInPanier(Panier panier);
	
	void deletePanier(Long idPanier);
}
