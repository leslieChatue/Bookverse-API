package com.chatue.bookverse.bookverse_api.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;

import com.chatue.bookverse.bookverse_api.dao.CategorieDao;
import com.chatue.bookverse.bookverse_api.dao.LivreDao;
import com.chatue.bookverse.bookverse_api.dto.LivreCompletDto;
import com.chatue.bookverse.bookverse_api.dto.LivreResumeDto;
import com.chatue.bookverse.bookverse_api.entity.Categorie;
import com.chatue.bookverse.bookverse_api.entity.Livre;
import com.chatue.bookverse.bookverse_api.exception.NullRessourceException;
import com.chatue.bookverse.bookverse_api.exception.RessourceNotFoundException;
import com.chatue.bookverse.bookverse_api.mapper.LivreMapper;
import com.chatue.bookverse.bookverse_api.service.LivreService;

/**
 * Service de la classe livre
 */
@Service
public class LivreServiceImpl implements LivreService {

	private final LivreDao livreDao;
	private final CategorieDao categorieDao;
	private final LivreMapper livreMapper;

	public LivreServiceImpl(LivreDao livreDao, CategorieDao categorieDao, LivreMapper livreMapper) {
		super();
		this.livreDao = livreDao;
		this.categorieDao = categorieDao;
		this.livreMapper = livreMapper;
	}

	@Override
	public List<LivreCompletDto> getAllLivres() {

		List<Livre> listeLivres = livreDao.findAll();
		if (listeLivres.isEmpty()) {
			throw new NullRessourceException("Aucun livre présent en base :LISTE_VIDE");
		}
		return livreMapper.toDtoListComplet(listeLivres);
	}

	@Override
	public List<LivreResumeDto> getAllLivresByTitreContaining(String titre) {

		List<Livre> listeLivres = livreDao.findAllLivresByTitreContainingIgnoreCase(titre);
		if (listeLivres.isEmpty()) {
			throw new NullRessourceException("Aucun livre présent en base avec pour titre :" + titre);
		}
		return livreMapper.toDtoListResume(listeLivres);
	}

	@Override
	public LivreCompletDto getLivreById(Long id) {

		Livre livre = livreDao.findById(id).orElseThrow(() -> new RessourceNotFoundException("Aucun livre trouvée avec l'id : " + id));
		
		return livreMapper.toDto(livre);
	}

	@Override
	public List<LivreResumeDto> getLivreByCategorieContaining(String nomCategorie) {
		// 1-> Je vérifie d'abord que la catégorie existe en base
		Categorie categorie = categorieDao.findCategorieByNom(nomCategorie);
		if (categorie == null) {
			throw new RessourceNotFoundException("Aucune catégorie trouvée avec l'id : " + nomCategorie);
		} else {
			List<Livre> listeLivres = livreDao.findByCategorieNomStartingWith(nomCategorie);

			if (listeLivres.isEmpty()) {
				throw new NullRessourceException("Aucun livre présent en base avec pour titre :" + nomCategorie);
			}
			return livreMapper.toDtoListResume(listeLivres);

		}
	}

	@Override
	public void savedLivre(Livre livre){
		if(livre==null) {
			throw new NullRessourceException("Impossible d'enregistrer ce livre car il est nul");
		}
		livreDao.save(livre);

	}

}
