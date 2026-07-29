package com.chatue.bookverse.bookverse_api.service;

import java.util.List;
import com.chatue.bookverse.bookverse_api.dto.PaiementResponseDTO;
import com.chatue.bookverse.bookverse_api.dto.request.PaiementRequest;
import com.chatue.bookverse.bookverse_api.entity.StatutPaiement;





public interface PaiementService {
	PaiementResponseDTO getPaiementByCommandeId(Long commandeId);

	List<PaiementResponseDTO> getByStatut(StatutPaiement statut);

	PaiementResponseDTO getPaiementById(Long id);

	void savePaiement(PaiementRequest paiementRequest);

	List<PaiementResponseDTO> getAllPaiements();

	void creerRemboursement( Long id);

	
}
