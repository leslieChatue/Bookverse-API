package com.chatue.bookverse.bookverse_api.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.chatue.bookverse.bookverse_api.dto.LivreCompletDto;
import com.chatue.bookverse.bookverse_api.dto.LivreResumeDto;
import com.chatue.bookverse.bookverse_api.dto.request.LivreRequest;
import com.chatue.bookverse.bookverse_api.dto.request.UpdateStockLivreRequest;

public interface LivreService {

	List<LivreCompletDto> getAllLivres();
	
	Page<LivreCompletDto> getAllLivresPageable(Pageable pageable);
	
	List<LivreResumeDto> getAllLivresByTitreContaining(String titre);
	
	LivreCompletDto getLivreById(Long id);
	
	List<LivreResumeDto> getLivreByCategorieContaining(String nomCategorie);
	
	LivreResumeDto savedLivre(LivreRequest livreRequest);
	
	LivreResumeDto updateLivre(Long id , LivreRequest livreRequest);
	
	void deleteLivre(Long id);
	
	List<LivreResumeDto> getLivreByAuteurId(Long auteurId);
	
	List<LivreResumeDto> getLivreByCategorieId(Long categorieId);

	Page<LivreCompletDto> getAllLivresByStockAndByPrix(BigDecimal minPrix, BigDecimal maxPrix, Boolean stockDisponible,
			Pageable pageable);

	LivreResumeDto updateStockLivre(Long id,  UpdateStockLivreRequest stock);
	
	boolean existsLivre(LivreRequest livreRequest);

	
}
