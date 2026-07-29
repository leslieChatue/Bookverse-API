package com.chatue.bookverse.bookverse_api.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chatue.bookverse.bookverse_api.dao.PanierDao;
import com.chatue.bookverse.bookverse_api.dao.UserDao;
import com.chatue.bookverse.bookverse_api.dto.PanierResponseDTO;
import com.chatue.bookverse.bookverse_api.dto.request.PanierModifierQuantite;
import com.chatue.bookverse.bookverse_api.dto.request.PanierRequest;
import com.chatue.bookverse.bookverse_api.entity.LignePanier;
import com.chatue.bookverse.bookverse_api.entity.Panier;
import com.chatue.bookverse.bookverse_api.entity.User;
import com.chatue.bookverse.bookverse_api.exception.RessourceNotFoundException;
import com.chatue.bookverse.bookverse_api.mapper.LignePanierMapper;
import com.chatue.bookverse.bookverse_api.mapper.PanierMapper;
import com.chatue.bookverse.bookverse_api.mapper.UserMapper;
import com.chatue.bookverse.bookverse_api.service.PanierService;
import com.chatue.bookverse.bookverse_api.service.UserService;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class PanierServiceImpl implements PanierService {
	private final PanierDao panierDao;
	private final UserDao userDao;
	private final UserService userService;
	private final PanierMapper panierMapper;
	private UserMapper userMapper;
	private LignePanierMapper lignePanierMapper;

	@Override
	@Transactional(readOnly = true)
	public PanierResponseDTO getUtilisateurById(Long utilisateurId) {
		return panierMapper.toDto(panierDao.findByUtilisateurId(utilisateurId)
				.orElseThrow(() -> new RessourceNotFoundException("Aucun utilisateur trouvé")));
	}

	@Override
	@Transactional
	public  void  savePanier(Long userId) {
		Panier panier = new Panier();
		User user = userDao.findById(userId)
				.orElseThrow(() -> new RessourceNotFoundException("Aucun utilisaeur trouvé avec cet id"));
		if (user != null && userDao.existsById(userId)) {
			panier.setUtilisateur(user);
			panierDao.save(panier);
		}
		//return panierMapper.toDto(panier);
	}

	@Override
	@Transactional(readOnly = true)
	public PanierResponseDTO getPanierById(Long id) {
		return panierMapper.toDto(panierDao.findById(id).orElseThrow(() -> new RessourceNotFoundException("Aucun utilisateur trouvé")));
	}

	

	@SuppressWarnings("null")
	@Override
	@Transactional
	public PanierResponseDTO saveLivreInPanier(PanierRequest panierRequest) {
		Panier panier = panierDao.findByUtilisateurId(panierRequest.getUserId()).orElseThrow(() -> new RessourceNotFoundException("Aucun utilisateur trouvé"));
		//1-> Je vérifie d'abord si le panier existe
		if(panier !=null) {
			panier.setUtilisateur(userMapper.toEntity(userService.getById(panierRequest.getUserId())));
			panier.setLignePanier(lignePanierMapper.toEntity(panierRequest.getLignes()));
			panierDao.saveLivreInPanier(panier); 
			} else { 
				panier.setDateCreation(LocalDateTime.now());
				panier.setUtilisateur(userMapper.toEntity(userService.getById(panierRequest.getUserId()))); 
				panier.setLignePanier(lignePanierMapper.toEntity(panierRequest.getLignes())); 
				panierDao.saveLivreInPanier(panier); 
			}
		return panierMapper.toDto(panier);
	}

	@Override
	@Transactional
	public void deleteLivrePanier(Long id, Long livreId) {	
		Panier panier = panierDao.findById(id).orElseThrow(() -> new RessourceNotFoundException("Aucun utilisaeur trouvé avec cet id"));
		if(panier !=null) {
			List<LignePanier> lignePanier = panier.getLignePanier();
			List<LignePanier> lignePanierFiltre =lignePanier.stream().filter(ligne -> ligne.getLivre().getId()!=livreId).toList();
			panier.setLignePanier(lignePanierFiltre);
			panierDao.save(panier);
		}
	}

	@Override
	@Transactional
	public void updateQuantite(PanierModifierQuantite panierModifierQuantite) {
		Panier panier = panierDao.findById(panierModifierQuantite.id()).orElseThrow(() -> new RessourceNotFoundException("Aucun utilisaeur trouvé avec cet id"));
		if(panier !=null) {
			List<LignePanier> ligneTrouve  = panier.getLignePanier();
			ligneTrouve.stream().filter(ligne -> ligne.getLivre().getId().equals(panierModifierQuantite.livreId()))
			.forEach(ligne -> ligne.setQuantite(panierModifierQuantite.quantite()));
			panier.setLignePanier(ligneTrouve);
			panierDao.save(panier);
		}
		
	}

	@Override
	public void viderPanier(Long id) {
		Panier panier = panierDao.findById(id).orElseThrow(() -> new RessourceNotFoundException("Aucun utilisaeur trouvé avec cet id"));
		if(panier !=null) {
		panierDao.deletePanier(id);
		}
		
		
	}

}
