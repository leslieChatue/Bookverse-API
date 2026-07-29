package com.chatue.bookverse.bookverse_api.service;

import java.util.List;

import com.chatue.bookverse.bookverse_api.dto.CategorieDto;
import com.chatue.bookverse.bookverse_api.dto.request.CategorieRequest;

public interface CategorieService {

	List<CategorieDto> getAllCategories();
	
	CategorieDto getCategorieById(Long id);
	
	CategorieDto getCategorieByNom(String nom);
	
	int updtCategorieByNom(String nouveauNom , Long id);

	int dltCategorieById(Long id);

	void savedCategorie(CategorieRequest categorie);
}
