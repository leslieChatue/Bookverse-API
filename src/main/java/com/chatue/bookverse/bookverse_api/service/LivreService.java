package com.chatue.bookverse.bookverse_api.service;

import java.util.List;

import com.chatue.bookverse.bookverse_api.dto.LivreCompletDto;
import com.chatue.bookverse.bookverse_api.dto.LivreResumeDto;
import com.chatue.bookverse.bookverse_api.entity.Livre;

public interface LivreService {

	List<LivreCompletDto> getAllLivres();
	
	List<LivreResumeDto> getAllLivresByTitreContaining(String titre);
	
	LivreCompletDto getLivreById(Long id);
	
	List<LivreResumeDto> getLivreByCategorieContaining(String nomCategorie);
	
	void savedLivre(Livre livre);

}
