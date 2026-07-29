package com.chatue.bookverse.bookverse_api.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chatue.bookverse.bookverse_api.dao.LignePanierDao;
import com.chatue.bookverse.bookverse_api.dto.LignePanierDTO;
import com.chatue.bookverse.bookverse_api.mapper.LignePanierMapper;
import com.chatue.bookverse.bookverse_api.service.LignePanierService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LignePanierServiceImpl implements LignePanierService {

	private final LignePanierDao lignePanierDao;
	private final LignePanierMapper lignePanierMapper;
	
	@Override
	@Transactional(readOnly = true)
	public List<LignePanierDTO> getPanierById(Long panierId) {
		return lignePanierMapper.toDtoList(lignePanierDao.findByPanierId(panierId));
	}

	@Override
	@Transactional
	public void dltByPanierIdAndLivreId(Long panierId, Long livreId) {
			lignePanierDao.deleteByPanierIdAndLivreId(panierId, livreId);
	}

}
