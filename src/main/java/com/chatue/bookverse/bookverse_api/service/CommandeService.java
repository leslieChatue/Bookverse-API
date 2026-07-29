package com.chatue.bookverse.bookverse_api.service;

import java.util.List;

import com.chatue.bookverse.bookverse_api.dto.CommandeResponseDTO;
import com.chatue.bookverse.bookverse_api.dto.DetailCommandeResponseDTO;
import com.chatue.bookverse.bookverse_api.dto.request.PaiementRequest;
import com.chatue.bookverse.bookverse_api.entity.StatutCommande;

import jakarta.validation.Valid;




public interface CommandeService {

	List<CommandeResponseDTO> getUtilisateurById(Long utilisateurId);
	
	List<CommandeResponseDTO> getCommandeByStatut(StatutCommande statut);
	
	CommandeResponseDTO getCommandeByNumeroCommande(String numero);
	
	DetailCommandeResponseDTO getDetailCommande(Long idCommande);

	void deleteCommande(Long id);

	void updateStatut(Long id,String statut);

	void saveCommande(Long panierId ,PaiementRequest paiementRequest);

	List<CommandeResponseDTO> getAllCommandes();
	
	List<CommandeResponseDTO> getAllCommandesByUser(Long userId);

	CommandeResponseDTO getCommandeById(@Valid Long id);
	
	//boolean existsByNumeroCommande(String numero);
}
