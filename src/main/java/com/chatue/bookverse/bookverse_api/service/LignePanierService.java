package com.chatue.bookverse.bookverse_api.service;

import java.util.List;

import com.chatue.bookverse.bookverse_api.dto.LignePanierDTO;

public interface LignePanierService {
List<LignePanierDTO> getPanierById(Long panierId);
	
	void dltByPanierIdAndLivreId(
			Long panierId,
			Long livreId
			);
}
