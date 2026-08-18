package com.chatue.bookverse.bookverse_api.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chatue.bookverse.bookverse_api.dao.CategorieDao;
import com.chatue.bookverse.bookverse_api.dto.CategorieDto;
import com.chatue.bookverse.bookverse_api.dto.request.CategorieRequest;
import com.chatue.bookverse.bookverse_api.entity.Categorie;
import com.chatue.bookverse.bookverse_api.exception.RessourceExistException;
import com.chatue.bookverse.bookverse_api.exception.RessourceNotFoundException;
import com.chatue.bookverse.bookverse_api.mapper.CategorieMapper;
import com.chatue.bookverse.bookverse_api.service.CategorieService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategorieServiceImpl implements CategorieService {

	private final CategorieDao categorieDao;
	private final CategorieMapper categorieMapper;

	

	@Override
	@Transactional(readOnly = true)
	public List<CategorieDto> getAllCategories() {
		return categorieMapper.toDtoList(categorieDao.findAllCategories());

	}

	@Override
	@Transactional(readOnly = true)
	public CategorieDto getCategorieById(Long id) {
			return	categorieMapper.toDto(categorieDao.findCategorieById(id).orElseThrow(()->new RessourceNotFoundException("Aucune catégorie trouvée avec l'id: " + id)));
}
	
	@Override
	@Transactional(readOnly = true)
	public CategorieDto getCategorieByNom(String nom) {
	return	categorieMapper.toDto(categorieDao.findCategorieByNom(nom).orElseThrow(()->new RessourceNotFoundException("Aucune catégorie trouvée avec le nom : " + nom)));
	}

	@Override
	@Transactional
	public CategorieDto updtCategorieByNom( Long id,CategorieRequest categorieRequest ) {
		// 1-> Je vérifie d'abord que la catégorie existe en base
		Categorie categorie = categorieDao.findCategorieById(id).orElseThrow(()->new RessourceNotFoundException("Aucune catégorie trouvée avec l'id : " + id));
		//2-> Je modifie son nom
		categorie.setNom(categorieRequest.getNom());
		//3-> J'enregistre les changements dans la base
		categorieDao.save(categorie);
		//4-> Je convertis en dto et je retourne l'objet dtto
		return categorieMapper.toDto(categorie);
			
	}

	@Override
	@Transactional
	public void dltCategorieById(Long id) {		
		Categorie categorie = categorieDao.findCategorieById(id).orElseThrow(()->new RessourceNotFoundException("Aucune catégorie trouvée avec l'id : " + id));
		categorieDao.deleteCategorieById(categorie.getId());		
	}

	@Override
	@Transactional
	public CategorieDto savedCategorie(CategorieRequest categorieRequest) {
		//1-> Je verifie si une categorie de ce genre n'existe pas déjà en base 
		if(existsCategorie(categorieRequest)) {
			throw new RessourceExistException("Impossible d'enregistrer cette catégorie car elle existe déjà : nom saisi= "+categorieRequest.getNom());
		}else {
			Categorie categorie= new Categorie();
			categorie.setNom(categorieRequest.getNom());
			categorieDao.save(categorie);
			return categorieMapper.toDto(categorie);
		}
		
	}

	@Override
	@Transactional(readOnly = true)
	public boolean existsCategorie(CategorieRequest categorieRequest) {
		return categorieDao.findCategorieByNom(categorieRequest.getNom()).isPresent();
	}

}
