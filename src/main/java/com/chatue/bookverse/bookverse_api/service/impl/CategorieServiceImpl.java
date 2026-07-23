package com.chatue.bookverse.bookverse_api.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.chatue.bookverse.bookverse_api.dao.CategorieDao;
import com.chatue.bookverse.bookverse_api.dto.CategorieDto;
import com.chatue.bookverse.bookverse_api.entity.Categorie;
import com.chatue.bookverse.bookverse_api.exception.NullRessourceException;
import com.chatue.bookverse.bookverse_api.exception.RessourceExistException;
import com.chatue.bookverse.bookverse_api.exception.RessourceNotFoundException;
import com.chatue.bookverse.bookverse_api.mapper.CategorieMapper;
import com.chatue.bookverse.bookverse_api.service.CategorieService;

@Service
public class CategorieServiceImpl implements CategorieService {

	private final CategorieDao categorieDao;
	private final CategorieMapper categorieMapper;

	public CategorieServiceImpl(CategorieDao categorieDao,CategorieMapper categorieMapper) {
		super();
		this.categorieDao = categorieDao;
		this.categorieMapper=categorieMapper;
	}

	@Override
	public List<CategorieDto> getAllCategories() {
		List<Categorie> listeCategorie = categorieDao.findAllCategories();
		if (listeCategorie.isEmpty()) {
			throw new NullRessourceException("Aucune categorie présentte dans la base de donnée");

		}
		return categorieMapper.toDtoList(listeCategorie);

	}

	@Override
	public CategorieDto getCategorieById(Long id) {
		Categorie categorie = categorieDao.findCategorieById(id);
		if (categorie == null) {
			throw new RessourceNotFoundException("Aucune catégorie trouvée avec l'id : " + id);
		}
		return categorieMapper.toDto(categorie);

	}
	
	@Override
	public CategorieDto getCategorieByNom(String nom) {
		Categorie categorie = categorieDao.findCategorieByNom(nom);
		if (categorie == null) {
			throw new RessourceNotFoundException("Aucune catégorie trouvée avec l'id : " + nom);
		}
		return categorieMapper.toDto(categorie);

	}

	@Override
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
	public void savedCategorie(Categorie cat) {
		//1-> Je verifie si une categorie de ce genre n'existe pas déjà en base 
		Categorie categorieTrouve = categorieDao.findCategorieByNom(cat.getNom());
		if(categorieTrouve!=null && categorieTrouve.equals(categorieTrouve)) {
			throw new RessourceExistException("Impossible d'enregistrer cette catégorie car elle existe déjà : nom saisi= "+cat.getNom());
		}else {
			categorieDao.saveAndFlush(cat);
		}
		
	}

}
