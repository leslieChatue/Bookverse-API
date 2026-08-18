package com.chatue.bookverse.bookverse_api.service;

import java.util.List;
import com.chatue.bookverse.bookverse_api.dto.PaiementResponseDTO;
import com.chatue.bookverse.bookverse_api.dto.request.PaiementRequest;
import com.chatue.bookverse.bookverse_api.dto.request.PaiementStatutRequest;

public interface PaiementService {
	PaiementResponseDTO getPaiementByCommandeId(Long commandeId);

	List<PaiementResponseDTO> getByStatut(PaiementStatutRequest paiementStatutRequest);

	PaiementResponseDTO getPaiementById(Long id);

	PaiementResponseDTO savePaiement(PaiementRequest paiementRequest);

	List<PaiementResponseDTO> getAllPaiements();

	void creerRemboursement( Long id);

}
