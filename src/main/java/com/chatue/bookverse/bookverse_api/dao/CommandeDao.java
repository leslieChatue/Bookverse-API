package com.chatue.bookverse.bookverse_api.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatue.bookverse.bookverse_api.entity.Commande;
import com.chatue.bookverse.bookverse_api.entity.StatutCommande;

public interface CommandeDao extends JpaRepository<Commande, Long> {

	List<Commande> findByUtilisateurId(Long utilisateurId);
	
	List<Commande> findByStatut(StatutCommande statut);
	
	Optional<Commande> findByNumeroCommande(String numero);
	
	boolean existsByNumeroCommande(String numero);
}
