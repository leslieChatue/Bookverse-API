package com.chatue.bookverse.bookverse_api.service;

import com.chatue.bookverse.bookverse_api.dto.PanierResponseDTO;
import com.chatue.bookverse.bookverse_api.dto.request.PanierModifierQuantite;
import com.chatue.bookverse.bookverse_api.dto.request.PanierRequest;


public interface PanierService {

	PanierResponseDTO getUtilisateurById(Long utilisateurId);
	PanierResponseDTO  savePanier(Long userId);
	PanierResponseDTO  saveLivreInPanier(PanierRequest panier);
	PanierResponseDTO  getPanierById( Long id);
	void deleteLivrePanier(Long id, Long livreId);
	PanierResponseDTO updateQuantite(PanierModifierQuantite panierModifierquantite);
	void viderPanier(Long id);
	
}
