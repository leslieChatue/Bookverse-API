package com.chatue.bookverse.bookverse_api.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chatue.bookverse.bookverse_api.dao.CommandeDao;
import com.chatue.bookverse.bookverse_api.dao.PaiementDao;
import com.chatue.bookverse.bookverse_api.dto.PaiementResponseDTO;
import com.chatue.bookverse.bookverse_api.dto.request.PaiementRequest;
import com.chatue.bookverse.bookverse_api.dto.request.PaiementStatutRequest;
import com.chatue.bookverse.bookverse_api.entity.ModePaiement;
import com.chatue.bookverse.bookverse_api.entity.StatutPaiement;
import com.chatue.bookverse.bookverse_api.exception.RessourceNotFoundException;
import com.chatue.bookverse.bookverse_api.mapper.PaiementMapper;
import com.chatue.bookverse.bookverse_api.service.PaiementService;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class PaiementServiceImpl implements PaiementService {

	private final PaiementDao paiementDao;
	private final CommandeDao commandeDao;
	private final PaiementMapper paiementMapper;
	
	
	@Override
	@Transactional(readOnly = true)
	public PaiementResponseDTO getPaiementByCommandeId(Long commandeId) {
		return paiementMapper.toDto(paiementDao.findByCommandeId(commandeId).orElseThrow(() -> new RessourceNotFoundException("Aucune commande trouvée pour ce paiement")));
	}

	@Override
	@Transactional(readOnly = true)
	public List<PaiementResponseDTO> getByStatut(PaiementStatutRequest paiementStatutRequest) {
		return paiementMapper.toDtoList(paiementDao.findByStatutPaiement(paiementStatutRequest.statutPaiement()));
	}

	@Override
	@Transactional(readOnly = true)
	public PaiementResponseDTO getPaiementById(Long id) {
		return paiementMapper.toDto(paiementDao.findById(id).orElseThrow(() -> new RessourceNotFoundException("Aucun paiement trouvée pour cet id")));
	}

	@Override
	@Transactional
	public PaiementResponseDTO savePaiement(PaiementRequest paiementRequest) {
		PaiementResponseDTO paiementResponseDTO = new PaiementResponseDTO();
		paiementResponseDTO.setCommandeId(paiementRequest.getCommandeId());
		paiementResponseDTO.setDatePaiement(LocalDateTime.now());
		paiementResponseDTO.setModePaiement(ModePaiement.valueOf(paiementRequest.getModePaiement()));
		paiementResponseDTO.setStatutPaiement(StatutPaiement.VALIDE);
		paiementResponseDTO.setMontant((commandeDao.findById(paiementRequest.getCommandeId())).get().getMontantTotal());
		paiementDao.save(paiementMapper.ToEntity(paiementResponseDTO));
		return paiementResponseDTO;
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
