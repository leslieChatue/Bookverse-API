package com.chatue.bookverse.bookverse_api.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chatue.bookverse.bookverse_api.dao.CategorieDao;
import com.chatue.bookverse.bookverse_api.dao.LivreDao;
import com.chatue.bookverse.bookverse_api.dao.LivreSpecifications;
import com.chatue.bookverse.bookverse_api.dto.LivreCompletDto;
import com.chatue.bookverse.bookverse_api.dto.LivreResumeDto;
import com.chatue.bookverse.bookverse_api.dto.request.LivreRequest;
import com.chatue.bookverse.bookverse_api.entity.Categorie;
import com.chatue.bookverse.bookverse_api.entity.Livre;
import com.chatue.bookverse.bookverse_api.exception.NullRessourceException;
import com.chatue.bookverse.bookverse_api.exception.RessourceNotFoundException;
import com.chatue.bookverse.bookverse_api.mapper.AuteurMapper;
import com.chatue.bookverse.bookverse_api.mapper.CategorieMapper;
import com.chatue.bookverse.bookverse_api.mapper.LivreMapper;
import com.chatue.bookverse.bookverse_api.service.AuteurService;
import com.chatue.bookverse.bookverse_api.service.CategorieService;
import com.chatue.bookverse.bookverse_api.service.LivreService;

import lombok.RequiredArgsConstructor;

/**
 * Service de la classe livre
 */
@Service
@RequiredArgsConstructor
public class LivreServiceImpl implements LivreService {

	private final LivreDao livreDao;
	private final CategorieDao categorieDao;
	private final CategorieService categorieService;
	private final AuteurService auteurService;
	private final LivreMapper livreMapper;
	private final AuteurMapper auteurMapper;
	private final CategorieMapper categorieMapper;

	

	@Override
	@Transactional(readOnly = true)
	public List<LivreCompletDto> getAllLivres() {

		List<Livre> listeLivres = livreDao.findAll();
		if (listeLivres.isEmpty()) {
			throw new NullRessourceException("Aucun livre présent en base :LISTE_VIDE");
		}
		return livreMapper.toDtoListComplet(listeLivres);
	}

	@Override
	@Transactional(readOnly = true)
	public List<LivreResumeDto> getAllLivresByTitreContaining(String titre) {

		List<Livre> listeLivres = livreDao.findAllLivresByTitreContainingIgnoreCase(titre);
		if (listeLivres.isEmpty()) {
			throw new NullRessourceException("Aucun livre présent en base avec pour titre :" + titre);
		}
		return livreMapper.toDtoListResume(listeLivres);
	}

	@Override
	@Transactional(readOnly = true)
	public LivreCompletDto getLivreById(Long id) {

		Livre livre = livreDao.findById(id).orElseThrow(() -> new RessourceNotFoundException("Aucun livre trouvée avec l'id : " + id));
		
		return livreMapper.toDto(livre);
	}

	@Override
	@Transactional(readOnly = true)
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
	@Transactional
	public void savedLivre(LivreRequest livreRequest){
			
		Livre livre = new Livre();
		LocalDateTime now = LocalDateTime.now();
		livre.setAuteur(auteurMapper.toEntity(auteurService.getAuteurById(livreRequest.getAuteurId())));
		livre.setCategorie(categorieMapper.toEntity(categorieService.getCategorieById(livreRequest.getCategorieId())));
		livre.setDateCreation(now);
		livre.setDescription(livreRequest.getDescription());
		livre.setIsbn(livreRequest.getIsbn());
		livre.setPrix(livreRequest.getPrix());
		livre.setStock(livreRequest.getStock());
		livre.setTitre(livreRequest.getTitre());
		livreDao.save(livre);
	}

	@Override
	@Transactional
	public void updateLivre(Long id ,LivreRequest livreRequest){
			
		Livre livre = livreDao.findById(id).orElseThrow(() -> new RessourceNotFoundException("Aucun livre trouvée avec l'id : " + id));
		if(livre!=null) {
		LocalDateTime now = LocalDateTime.now();
		livre.setAuteur(auteurMapper.toEntity(auteurService.getAuteurById(livreRequest.getAuteurId())));
		livre.setCategorie(categorieMapper.toEntity(categorieService.getCategorieById(livreRequest.getCategorieId())));
		livre.setDateModification(now);
		livre.setDescription(livreRequest.getDescription());
		livre.setIsbn(livreRequest.getIsbn());
		livre.setPrix(livreRequest.getPrix());
		livre.setStock(livreRequest.getStock());
		livre.setTitre(livreRequest.getTitre());
		livreDao.save(livre);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<LivreResumeDto> getLivreByAuteurId(Long auteurId) {
		return livreMapper.toDtoListResume(livreDao.findByAuteurId(auteurId));
	}

	@Override
	@Transactional(readOnly = true)
	public List<LivreResumeDto> getLivreByCategorieId(Long categorieId) {
		return livreMapper.toDtoListResume(livreDao.findByCategorieId(categorieId));
	}

	
	@Override
	public int deleteLivre(Long id) {
		int cpt =0;
		Livre livre= livreDao.findById(id).orElseThrow(() -> new NullRessourceException("Aucun livre présent en base "));
		if(livre != null && livreDao.existsById(livre.getId())) {
			livreDao.delete(livre);
			cpt++;
		}
		return cpt;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<LivreCompletDto> getAllLivresPageable(Pageable pageable) {
		Page<Livre> listeLivres = livreDao.findAll(pageable);
		if (listeLivres.isEmpty()) {
			throw new NullRessourceException("Aucun livre présent en base :LISTE_VIDE");
		}
		return (Page<LivreCompletDto>) livreMapper.toDtoListComplet((List<Livre>) listeLivres);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Page<LivreCompletDto> getAllLivresByStockAndByPrix(BigDecimal minPrix, BigDecimal maxPrix,
			Boolean stockDisponible ,Pageable pageable) {
		Specification<Livre> spec = Specification
                .where(LivreSpecifications.prixEntre(minPrix, maxPrix))
                .and(LivreSpecifications.stockDispo(stockDisponible));
        return  (Page<LivreCompletDto>) livreMapper.toDtoListComplet((List<Livre>) livreDao.findAll(spec, pageable));
	
	}

	@Override
	public void updateStockLivre(Long id, Integer stock) {
		Livre livre = livreDao.findById(id).orElseThrow(() -> new RessourceNotFoundException("Aucun livre trouvée avec l'id : " + id));
		if(livre!=null) {
			livre.setStock(stock);
			livreDao.save(livre);
		}
		
	}

}
