package com.chatue.bookverse.bookverse_api.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatue.bookverse.bookverse_api.entity.Paiement;
import com.chatue.bookverse.bookverse_api.entity.StatutPaiement;

public interface PaiementDao extends JpaRepository<Paiement, Long> {
	Optional<Paiement> findByCommandeId(Long commandeId);
	
	List<Paiement> findByStatut(StatutPaiement statut);
}
