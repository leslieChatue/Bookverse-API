package com.chatue.bookverse.bookverse_api.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chatue.bookverse.bookverse_api.entity.Commande;
import com.chatue.bookverse.bookverse_api.entity.StatutCommande;

@Repository
public interface CommandeDao extends JpaRepository<Commande, Long> {

	List<Commande> findByUtilisateurId(Long utilisateurId);
	
	List<Commande> findByStatut(StatutCommande statut);
	
	Optional<Commande> findByNumeroCommande(String numero);
	
	List<Commande> findAllCommandes();
	
	List<Commande> findAllCommandesByUser(Long userId);
	
	boolean existsByNumeroCommande(String numero);
	
	
}
