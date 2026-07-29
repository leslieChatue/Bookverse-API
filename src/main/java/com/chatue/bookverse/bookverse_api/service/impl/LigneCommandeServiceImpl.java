package com.chatue.bookverse.bookverse_api.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chatue.bookverse.bookverse_api.dao.LigneCommandeDao;
import com.chatue.bookverse.bookverse_api.dto.LigneCommandeDTO;
import com.chatue.bookverse.bookverse_api.mapper.LigneCommandeMapper;
import com.chatue.bookverse.bookverse_api.service.LigneCommandeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LigneCommandeServiceImpl implements LigneCommandeService {

	private final LigneCommandeDao ligneCommandeDao;
	private final LigneCommandeMapper ligneCommandeMapper;
	
	@Override
	@Transactional(readOnly = true)
	public List<LigneCommandeDTO> getLigneCommandeByIdCommande(Long commandeId) {
		return ligneCommandeMapper.toDtoList(ligneCommandeDao.findLigneCommandeByIdCommande(commandeId));
	}

}
