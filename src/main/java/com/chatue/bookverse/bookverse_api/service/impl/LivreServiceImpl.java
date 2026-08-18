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
import com.chatue.bookverse.bookverse_api.dto.request.UpdateStockLivreRequest;
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

	private static final  String LIVRE_NOT_FOUND ="Aucun livre trouvé avec cet id: ";
	private static final  String CATEGORIE_NOT_FOUND ="Aucune categorie trouvée avec ce nom: ";

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
		return livreMapper.toDtoListComplet(livreDao.findAll());
	}

	@Override
	@Transactional(readOnly = true)
	public List<LivreResumeDto> getAllLivresByTitreContaining(String titre) {
		return livreMapper.toDtoListResume(livreDao.findByTitreContainingIgnoreCase(titre));
	}

	@Override
	@Transactional(readOnly = true)
	public LivreCompletDto getLivreById(Long id) {
		Livre livre = livreDao.findById(id).orElseThrow(() -> new RessourceNotFoundException(LIVRE_NOT_FOUND + id));
		return livreMapper.toDto(livre);
	}

	@Override
	@Transactional(readOnly = true)
	public List<LivreResumeDto> getLivreByCategorieContaining(String nomCategorie) {
		// 1-> Je vérifie d'abord que la catégorie existe en base
		Categorie categorie = categorieDao.findCategorieByNom(nomCategorie).orElseThrow(() -> new RessourceNotFoundException(CATEGORIE_NOT_FOUND + nomCategorie));;
			List<Livre> listeLivres = livreDao.findByCategorieNomStartingWith(categorie.getNom());
			return livreMapper.toDtoListResume(listeLivres);
	}

	@Override
	@Transactional
	public LivreResumeDto savedLivre(LivreRequest livreRequest){
		//1-> Je vérifie que le livre n'existe pas déjà
		
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
		return livreMapper.toDtoResume(livre);
	}

	@Override
	@Transactional
	public LivreResumeDto updateLivre(Long id ,LivreRequest livreRequest){
			
		Livre livre = livreDao.findById(id).orElseThrow(() -> new RessourceNotFoundException(LIVRE_NOT_FOUND + id));
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
		return livreMapper.toDtoResume(livre);
		
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
	@Transactional
	public void deleteLivre(Long id) {
	
		Livre livre= livreDao.findById(id).orElseThrow(() -> new NullRessourceException(LIVRE_NOT_FOUND +" "+id));
		if(livreDao.existsById(livre.getId())) {
			livreDao.delete(livre);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<LivreCompletDto> getAllLivresPageable(Pageable pageable) {
		Page<Livre> listeLivres = livreDao.findAll(pageable);
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
	@Transactional
	public LivreResumeDto updateStockLivre(Long id,UpdateStockLivreRequest stock) {
			Livre livre = livreDao.findById(id).orElseThrow(() -> new RessourceNotFoundException("Aucun livre trouvée avec l'id : " + id));
			livre.setStock(stock.stock());
			livreDao.save(livre);
			return livreMapper.toDtoResume(livre);	
	}

	@Override
	@Transactional(readOnly = true)
	public boolean existsLivre(LivreRequest livreRequest) {
		return livreDao.existsByIsbn(livreRequest.getIsbn());
	}

}
