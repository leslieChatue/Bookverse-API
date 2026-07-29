package com.chatue.bookverse.bookverse_api.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chatue.bookverse.bookverse_api.dao.CategorieDao;
import com.chatue.bookverse.bookverse_api.dto.CategorieDto;
import com.chatue.bookverse.bookverse_api.dto.request.CategorieRequest;
import com.chatue.bookverse.bookverse_api.entity.Categorie;
import com.chatue.bookverse.bookverse_api.exception.NullRessourceException;
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
		List<Categorie> listeCategorie = categorieDao.findAllCategories();
		if (listeCategorie.isEmpty()) {
			throw new NullRessourceException("Aucune categorie présentte dans la base de donnée");

		}
		return categorieMapper.toDtoList(listeCategorie);

	}

	@Override
	@Transactional(readOnly = true)
	public CategorieDto getCategorieById(Long id) {
		Categorie categorie = categorieDao.findCategorieById(id);
		if (categorie == null) {
			throw new RessourceNotFoundException("Aucune catégorie trouvée avec l'id : " + id);
		}
		return categorieMapper.toDto(categorie);

	}
	
	@Override
	@Transactional(readOnly = true)
	public CategorieDto getCategorieByNom(String nom) {
		Categorie categorie = categorieDao.findCategorieByNom(nom);
		if (categorie == null) {
			throw new RessourceNotFoundException("Aucune catégorie trouvée avec l'id : " + nom);
		}
		return categorieMapper.toDto(categorie);

	}

	@Override
	@Transactional
	public int updtCategorieByNom(String nouveauNom, Long id) {
		int nbreCategorieModifiee = 0;
		// 1-> Je vérifie d'abord que la catégorie existe en base
		Categorie categorie = categorieDao.findCategorieById(id);
		if (categorie == null) {
			throw new RessourceNotFoundException("Aucune catégorie trouvée avec l'id : " + id);
		} else {
			nbreCategorieModifiee = categorieDao.updateCategorieByNom(nouveauNom, id);
			}
		return nbreCategorieModifiee;
	}

	@Override
	@Transactional
	public int dltCategorieById(Long id) {		
		int nbreCategorieModifiee = 0;
		// 1-> Je vérifie d'abord que la catégorie existe en base
		Categorie categorie = categorieDao.findCategorieById(id);
		if (categorie == null) {
			throw new RessourceNotFoundException("Aucune catégorie trouvée avec l'id : " + id);
		} else {
			nbreCategorieModifiee =categorieDao.deleteCategorieById(id);
		}
		return nbreCategorieModifiee;
		
	}

	@Override
	@Transactional
	public void savedCategorie(CategorieRequest cat) {
		//1-> Je verifie si une categorie de ce genre n'existe pas déjà en base 
		Categorie categorieTrouve = categorieDao.findCategorieByNom(cat.getNom());
		if(categorieTrouve!=null && categorieTrouve.equals(categorieTrouve)) {
			throw new RessourceExistException("Impossible d'enregistrer cette catégorie car elle existe déjà : nom saisi= "+cat.getNom());
		}else {
			Categorie cat1= new Categorie();
			cat1.setNom(cat.getNom());
			categorieDao.save(cat1);
		}
		
	}

}
