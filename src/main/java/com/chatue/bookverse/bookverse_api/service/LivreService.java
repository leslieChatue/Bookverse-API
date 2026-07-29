package com.chatue.bookverse.bookverse_api.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.chatue.bookverse.bookverse_api.dto.LivreCompletDto;
import com.chatue.bookverse.bookverse_api.dto.LivreResumeDto;
import com.chatue.bookverse.bookverse_api.dto.request.LivreRequest;




public interface LivreService {

	List<LivreCompletDto> getAllLivres();
	
	Page<LivreCompletDto> getAllLivresPageable(Pageable pageable);
	
	List<LivreResumeDto> getAllLivresByTitreContaining(String titre);
	
	LivreCompletDto getLivreById(Long id);
	
	List<LivreResumeDto> getLivreByCategorieContaining(String nomCategorie);
	
	void savedLivre(LivreRequest livreRequest);
	
	void updateLivre(Long id , LivreRequest livreRequest);
	
	int deleteLivre(Long id);
	
	List<LivreResumeDto> getLivreByAuteurId(Long auteurId);
	
	List<LivreResumeDto> getLivreByCategorieId(Long categorieId);

	Page<LivreCompletDto> getAllLivresByStockAndByPrix(BigDecimal minPrix, BigDecimal maxPrix, Boolean stockDisponible,
			Pageable pageable);

	void updateStockLivre(Long id,  Integer stock);

	
}
