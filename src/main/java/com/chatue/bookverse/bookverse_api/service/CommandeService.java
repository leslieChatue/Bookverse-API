package com.chatue.bookverse.bookverse_api.service;

import java.util.List;

import com.chatue.bookverse.bookverse_api.dto.CommandeResponseDTO;
import com.chatue.bookverse.bookverse_api.dto.DetailCommandeResponseDTO;
import com.chatue.bookverse.bookverse_api.dto.request.PaiementRequest;
import com.chatue.bookverse.bookverse_api.dto.request.UpdateCommandRequest;
import com.chatue.bookverse.bookverse_api.entity.StatutCommande;


public interface CommandeService {

	List<CommandeResponseDTO> getUtilisateurById(Long utilisateurId);
	
	List<CommandeResponseDTO> getCommandeByStatut(StatutCommande statut);
	
	CommandeResponseDTO getCommandeByNumeroCommande(String numero);
	
	DetailCommandeResponseDTO getDetailCommande(Long idCommande);

	void deleteCommande(Long id);

	CommandeResponseDTO updateStatut(Long id,UpdateCommandRequest updateCommandRequest);

	CommandeResponseDTO saveCommande(Long panierId ,PaiementRequest paiementRequest);

	List<CommandeResponseDTO> getAllCommandes();
	
	List<CommandeResponseDTO> getAllCommandesByUserId(Long userId);

	CommandeResponseDTO getCommandeById(Long id);
	
	boolean existsByPanierUserId(Long userId);
}
