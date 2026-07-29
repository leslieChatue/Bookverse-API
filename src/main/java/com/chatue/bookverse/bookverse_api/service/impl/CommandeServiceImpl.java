package com.chatue.bookverse.bookverse_api.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chatue.bookverse.bookverse_api.dao.CommandeDao;
import com.chatue.bookverse.bookverse_api.dao.PanierDao;
import com.chatue.bookverse.bookverse_api.dto.CommandeResponseDTO;
import com.chatue.bookverse.bookverse_api.dto.DetailCommandeResponseDTO;
import com.chatue.bookverse.bookverse_api.dto.LigneCommandeDTO;
import com.chatue.bookverse.bookverse_api.dto.LignePanierDTO;
import com.chatue.bookverse.bookverse_api.dto.PanierResponseDTO;
import com.chatue.bookverse.bookverse_api.dto.request.PaiementRequest;
import com.chatue.bookverse.bookverse_api.entity.Commande;
import com.chatue.bookverse.bookverse_api.entity.Livre;
import com.chatue.bookverse.bookverse_api.entity.Panier;
import com.chatue.bookverse.bookverse_api.entity.StatutCommande;
import com.chatue.bookverse.bookverse_api.exception.RessourceNotFoundException;
import com.chatue.bookverse.bookverse_api.mapper.CommandeMapper;
import com.chatue.bookverse.bookverse_api.mapper.LivreMapper;
import com.chatue.bookverse.bookverse_api.service.CommandeService;
import com.chatue.bookverse.bookverse_api.service.LigneCommandeService;
import com.chatue.bookverse.bookverse_api.service.LivreService;
import com.chatue.bookverse.bookverse_api.service.PaiementService;
import com.chatue.bookverse.bookverse_api.service.PanierService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommandeServiceImpl implements CommandeService {

	private final CommandeDao commandeDao;
	private final CommandeMapper commandeMapper;
	private final LigneCommandeService ligneCommandeService;
	private final PanierService panierService;
	private final PanierDao panierDao;
	private final PaiementService paiementService;
	private final LivreService livreService;
	private final LivreMapper livreMapper;
	
	@Override
	@Transactional(readOnly = true)
	public List<CommandeResponseDTO> getUtilisateurById(Long utilisateurId) {
		return commandeMapper.toDtoList(commandeDao.findByUtilisateurId(utilisateurId));
	}

	@Override
	@Transactional(readOnly = true)
	public List<CommandeResponseDTO> getCommandeByStatut(StatutCommande statut) {
		return commandeMapper.toDtoList(commandeDao.findByStatut(statut));
	}

	@Override
	@Transactional(readOnly = true)
	public CommandeResponseDTO getCommandeByNumeroCommande(String numero) {
		return commandeMapper.toDto(commandeDao.findByNumeroCommande(numero).orElseThrow(() -> new RessourceNotFoundException("Aucune commande trouvée avec ce numéro de commande")));
	}

	@Override
	@Transactional(readOnly = true)
	public DetailCommandeResponseDTO getDetailCommande(Long idCommande) {
		
		CommandeResponseDTO commandeDto= commandeMapper.toDto(commandeDao.findById(idCommande).orElseThrow(() -> new RessourceNotFoundException("Aucune commande trouvée avec cet id de commande")));
		
		List<LigneCommandeDTO> ligneCommande = ligneCommandeService.getLigneCommandeByIdCommande(idCommande);
		
		DetailCommandeResponseDTO detailCommandeDto=new DetailCommandeResponseDTO();
		
		detailCommandeDto.setCommandeDto(commandeDto);
		detailCommandeDto.setLigneCommande(ligneCommande);
		
	
		return detailCommandeDto;
	}

	@Override
	@Transactional
	public void deleteCommande(Long id) {
		Commande commande =commandeDao.findById(id).orElseThrow(() -> new RessourceNotFoundException("Aucune commande trouvée avec cet id de commande"));
		if(commande != null) {
			//On vide le panier de l'utilisateur???
			commandeDao.delete(commande);
		}
	}

	@Override
	@Transactional
	public void updateStatut(Long id, String statut) {
		Commande commande =commandeDao.findById(id).orElseThrow(() -> new RessourceNotFoundException("Aucune commande trouvée avec cet id de commande"));
		if(commande != null) {
			commande.setStatut(StatutCommande.valueOf(statut));
			commandeDao.save(commande);
		}
		
	}

	@Override
	@Transactional
	public void saveCommande(Long panierId, PaiementRequest paiementRequest ) {
		// 1-> Recuperer le panier
		int totalCommande= (int) commandeDao.count();
		PanierResponseDTO panier = panierService.getPanierById(panierId);
		// 2-> a partir des informations du panier remplir la commande
		Commande commande = new Commande();
		commande.setDateCommande(LocalDateTime.now());
		BigDecimal somme = panier.getLignePanier()
                .stream()
                .map(LignePanierDTO::getSousTotal) // retourne BigDecimal
                .reduce(BigDecimal.ZERO, BigDecimal::add);
		commande.setNumeroCommande("COMMANDE-"+totalCommande+1);
		commande.setMontantTotal(somme);
		//On associe la commande à un user
		commande.setUserId(panier.getUserId());
		//2.1 -> statut passe automatiquement à créer
		commande.setStatut(StatutCommande.CREE);
		//2.2 -> on modifie la quantité de stock de "chaque" livre commandée en fonction de la quantité achetée
		List<LignePanierDTO> lignePanier = panier.getLignePanier();
		for(int i=0 ; i<lignePanier.size() ; i++) {
			Livre livreTrouve=livreMapper.toEntity(livreService.getLivreById(lignePanier.get(i).getLivreId()));
			//Appeler mon service de modification du stock 
			int nouveauStock=livreTrouve.getStock()-lignePanier.get(i).getQuantite();
			livreService.updateStockLivre(lignePanier.get(i).getLivreId() ,nouveauStock );
			
		}
		//2.3 ->on supprime le panier associé a l'utilisateur dans la base de donnée
		Panier panierTrouve = panierDao.findById(panierId).orElseThrow(() -> new RessourceNotFoundException("Aucun livre trouvé avec cet id"));
		panierDao.delete(panierTrouve);
		//2.4 -> on enregistre le tout(commande , livre , panier)
		commandeDao.save(commande);
		//2.5 -> on passe au paiement (à implémenter plus tard)	
		paiementService.savePaiement(paiementRequest);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CommandeResponseDTO> getAllCommandes() {
		
		return commandeMapper.toDtoList(commandeDao.findAllCommandes());
	}

	@Override
	@Transactional(readOnly = true)
	public List<CommandeResponseDTO> getAllCommandesByUser(Long userId) {
		
		 return commandeMapper.toDtoList(commandeDao.findAllCommandesByUser(userId));
	}
	
	@Override
	@Transactional(readOnly = true)
	public CommandeResponseDTO getCommandeById(Long id) {
		return commandeMapper.toDto(commandeDao.findById(id).orElseThrow(() -> new RessourceNotFoundException("Aucun livre trouvé avec cet id")));
	}

}
