package com.chatue.bookverse.bookverse_api.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chatue.bookverse.bookverse_api.dao.PanierDao;
import com.chatue.bookverse.bookverse_api.dto.PanierResponseDTO;
import com.chatue.bookverse.bookverse_api.dto.UserResponseDTO;
import com.chatue.bookverse.bookverse_api.dto.request.PanierModifierQuantite;
import com.chatue.bookverse.bookverse_api.dto.request.PanierRequest;
import com.chatue.bookverse.bookverse_api.entity.LignePanier;
import com.chatue.bookverse.bookverse_api.entity.Panier;
import com.chatue.bookverse.bookverse_api.exception.RessourceExistException;
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

	private static final String USER_NOT_FOUND = "Aucun utilisateur trouvé avec cet id :";

	private final PanierDao panierDao;
	private final UserService userService;
	private final PanierMapper panierMapper;
	private UserMapper userMapper;
	private LignePanierMapper lignePanierMapper;

	@Override
	@Transactional(readOnly = true)
	public PanierResponseDTO getUtilisateurById(Long utilisateurId) {
		return panierMapper.toDto(panierDao.findByUserId(utilisateurId)
				.orElseThrow(() -> new RessourceNotFoundException(USER_NOT_FOUND + " " + utilisateurId)));
	}

	@Override
	@Transactional
	public PanierResponseDTO savePanier(Long userId) {
		// 1-> Verifier qu'on a pas déjà un panier pour cet user
		if (panierDao.existsByUserId(userId)) {
			throw new RessourceExistException("Un panier existe déjà pour cet utilisateur :" + userId);
		}
		UserResponseDTO user = userService.getById(userId);
		Panier panier = new Panier();
		panier.setUser(userMapper.toEntity(user));
		panierDao.save(panier);
		return panierMapper.toDto(panier);
	}

	@Override
	@Transactional(readOnly = true)
	public PanierResponseDTO getPanierById(Long id) {
		return panierMapper
				.toDto(panierDao.findById(id).orElseThrow(() -> new RessourceNotFoundException(USER_NOT_FOUND)));
	}

	@Override
	@Transactional
	public PanierResponseDTO saveLivreInPanier(PanierRequest panierRequest) {
		// 1-> Je vérifie d'abord si le panier existe
		if(panierDao.existsByUserId(panierRequest.getUserId())){
				throw new RessourceExistException("Aucun panier trouvé pour cet utilisateur");
		}
		Panier panier = new Panier();
		panier.setUser(userMapper.toEntity(userService.getById(panierRequest.getUserId())));
		panier.setLignePanier(lignePanierMapper.toEntity(panierRequest.getLignes()));
		panier.setDateCreation(LocalDate.now());
		panierDao.save(panier);
		return panierMapper.toDto(panier);
	}

	@Override
	@Transactional
	public void deleteLivrePanier(Long id, Long livreId) {
		Panier panier = panierDao.findById(id)
				.orElseThrow(() -> new RessourceNotFoundException(USER_NOT_FOUND + " " + livreId));
		if (panier != null) {
			List<LignePanier> lignePanier = panier.getLignePanier();
			List<LignePanier> lignePanierFiltre = lignePanier.stream()
					.filter(ligne -> !Objects.equals(ligne.getLivre().getId(), livreId)).toList();
			panier.setLignePanier(lignePanierFiltre);
			panierDao.save(panier);
		}
	}

	@Override
	@Transactional
	public PanierResponseDTO updateQuantite(PanierModifierQuantite panierModifierQuantite) {
		Panier panier = panierDao.findById(panierModifierQuantite.id())
				.orElseThrow(() -> new RessourceNotFoundException("Aucun utilisaeur trouvé avec cet id"));
		List<LignePanier> ligneTrouve = panier.getLignePanier();
		ligneTrouve.stream().filter(ligne -> ligne.getLivre().getId().equals(panierModifierQuantite.livreId()))
				.forEach(ligne -> ligne.setQuantite(panierModifierQuantite.quantite()));
		panier.setLignePanier(ligneTrouve);
		panierDao.save(panier);
		return panierMapper.toDto(panier);
	}

	@Override
	@Transactional
	public void viderPanier(Long id) {
		Panier panier = panierDao.findById(id)
				.orElseThrow(() -> new RessourceNotFoundException("Aucun Panier trouvé avec cet id"));
		panierDao.deleteById(panier.getId());
	}

}
