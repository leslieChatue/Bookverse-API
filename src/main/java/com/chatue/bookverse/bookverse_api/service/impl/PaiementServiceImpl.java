package com.chatue.bookverse.bookverse_api.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chatue.bookverse.bookverse_api.dao.PaiementDao;
import com.chatue.bookverse.bookverse_api.dto.PaiementResponseDTO;
import com.chatue.bookverse.bookverse_api.dto.request.PaiementRequest;
import com.chatue.bookverse.bookverse_api.entity.ModePaiement;
import com.chatue.bookverse.bookverse_api.entity.Paiement;
import com.chatue.bookverse.bookverse_api.entity.StatutPaiement;
import com.chatue.bookverse.bookverse_api.exception.RessourceNotFoundException;
import com.chatue.bookverse.bookverse_api.mapper.CommandeMapper;
import com.chatue.bookverse.bookverse_api.mapper.PaiementMapper;
import com.chatue.bookverse.bookverse_api.service.CommandeService;
import com.chatue.bookverse.bookverse_api.service.PaiementService;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class PaiementServiceImpl implements PaiementService {

	private final PaiementDao paiementDao;
	private final PaiementMapper paiementMapper;
	private final CommandeMapper commandeMapper;
	private final CommandeService commandeService;
	
	@Override
	@Transactional(readOnly = true)
	public PaiementResponseDTO getPaiementByCommandeId(Long commandeId) {
		return paiementMapper.toDto(paiementDao.findByCommandeId(commandeId).orElseThrow(() -> new RessourceNotFoundException("Aucune commande trouvée pour ce paiement")));
	}

	@Override
	@Transactional(readOnly = true)
	public List<PaiementResponseDTO> getByStatut(StatutPaiement statut) {
		return paiementMapper.toDtoList(paiementDao.findByStatut(statut));
	}

	@Override
	@Transactional(readOnly = true)
	public PaiementResponseDTO getPaiementById(Long id) {
		return paiementMapper.toDto(paiementDao.findById(id).orElseThrow(() -> new RessourceNotFoundException("Aucun paiement trouvée pour cet id")));
	}

	@Override
	@Transactional
	public void savePaiement(PaiementRequest paiementRequest) {
		Paiement paiement= new Paiement();
		paiement.setCommande(commandeMapper.toEntity(commandeService.getCommandeById(paiementRequest.getCommandeId())));
		paiement.setDatePaiement(LocalDateTime.now());
		paiement.setModePaiement(ModePaiement.valueOf(paiementRequest.getModePaiement()));
		paiement.setStatutPaiement(StatutPaiement.VALIDE);
		paiement.setMontant(commandeMapper.toEntity(commandeService.getCommandeById(paiementRequest.getCommandeId())).getMontantTotal());
		paiementDao.save(paiement);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PaiementResponseDTO> getAllPaiements() {
		return paiementMapper.toDtoList(paiementDao.findAll());
	}

	@Override
	@Transactional
	public void creerRemboursement(Long id) {
		// TODO Auto-generated method stub
		
	}

}
