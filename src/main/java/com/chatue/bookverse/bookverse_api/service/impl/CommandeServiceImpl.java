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
import com.chatue.bookverse.bookverse_api.dto.request.UpdateCommandRequest;
import com.chatue.bookverse.bookverse_api.dto.request.UpdateStockLivreRequest;
import com.chatue.bookverse.bookverse_api.entity.Commande;
import com.chatue.bookverse.bookverse_api.entity.Livre;
import com.chatue.bookverse.bookverse_api.entity.Panier;
import com.chatue.bookverse.bookverse_api.entity.StatutCommande;
import com.chatue.bookverse.bookverse_api.exception.RessourceExistException;
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
		return commandeMapper.toDtoList(commandeDao.findByUserId(utilisateurId));
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
	public CommandeResponseDTO updateStatut(Long id , UpdateCommandRequest updateCommandRequest) {
			Commande commande =commandeDao.findById(id).orElseThrow(() -> new RessourceNotFoundException("Aucune commande trouvée avec cet id de commande : "+id));
			commande.setStatut(StatutCommande.valueOf(updateCommandRequest.statut()));
			commandeDao.save(commande);
			return commandeMapper.toDto(commande);
	}

	@Override
	@Transactional
	public CommandeResponseDTO saveCommande(Long panierId, PaiementRequest paiementRequest ) {
		// 1-> Recuperer le panier
		int totalCommande = (int) commandeDao.count();
		PanierResponseDTO panier = panierService.getPanierById(panierId);
		//1.1 -> Je vérifie qu'il n'ya pas déja d'utilisateur avec cette commande
		if(existsByPanierUserId(panier.getUserId())) {
			throw new RessourceExistException("Impossible d'ajouter cette commande car une commande est déja attachée a ce panier");
		}
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
			livreService.updateStockLivre(lignePanier.get(i).getLivreId() ,new UpdateStockLivreRequest(nouveauStock) );	
		}
		//2.3 ->on supprime le panier associé a l'utilisateur dans la base de donnée
		Panier panierTrouve = panierDao.findById(panierId).orElseThrow(() -> new RessourceNotFoundException("Aucun livre trouvé avec cet id"));
		panierDao.delete(panierTrouve);
		//2.4 -> on enregistre le tout(commande , livre , panier)
		commandeDao.save(commande);
		//2.5 -> on passe au paiement (à implémenter plus tard)	
		paiementService.savePaiement(paiementRequest);
		//2.6 -> Si tout s'est bien passé on retourne la commande
		return commandeMapper.toDto(commande);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CommandeResponseDTO> getAllCommandes() {
		return commandeMapper.toDtoList(commandeDao.findAll());
	}

	@Override
	@Transactional(readOnly = true)
	public List<CommandeResponseDTO> getAllCommandesByUserId(Long userId) {
		 return commandeMapper.toDtoList(commandeDao.findByUserId(userId));
	}
	
	@Override
	@Transactional(readOnly = true)
	public CommandeResponseDTO getCommandeById(Long id) {
		return commandeMapper.toDto(commandeDao.findById(id).orElseThrow(() -> new RessourceNotFoundException("Aucun livre trouvé avec cet id")));
	}

	@Override
	public boolean existsByPanierUserId(Long userId) {
		
		return panierDao.existsByUserId(userId);
	}

}
