package com.chatue.bookverse.bookverse_api.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chatue.bookverse.bookverse_api.dao.AuteurDao;
import com.chatue.bookverse.bookverse_api.dto.AuteurDto;
import com.chatue.bookverse.bookverse_api.dto.LivreCompletDto;
import com.chatue.bookverse.bookverse_api.dto.request.AuteurRequest;
import com.chatue.bookverse.bookverse_api.entity.Auteur;
import com.chatue.bookverse.bookverse_api.entity.Livre;
import com.chatue.bookverse.bookverse_api.exception.NullRessourceException;
import com.chatue.bookverse.bookverse_api.exception.RessourceExistException;
import com.chatue.bookverse.bookverse_api.exception.RessourceNotFoundException;
import com.chatue.bookverse.bookverse_api.mapper.AuteurMapper;
import com.chatue.bookverse.bookverse_api.mapper.LivreMapper;
import com.chatue.bookverse.bookverse_api.service.AuteurService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuteurServiceImpl implements AuteurService {

	private final AuteurDao auteurDao;
	private final AuteurMapper auteurMapper;
	private final LivreMapper livreMapper;

	@Override
	@Transactional(readOnly = true)
	public List<AuteurDto> getAllAuteurs() {

		List<Auteur> listeAuteurs = auteurDao.findAllAuteurs();
		if (listeAuteurs.isEmpty()) {
			throw new NullRessourceException("Aucun auteur présent en base ");
		}
		return auteurMapper.toDtoList(listeAuteurs);

	}

	@Override
	@Transactional(readOnly = true)
	public AuteurDto getAuteurById(Long id) {

		Auteur auteur = auteurDao.findAuteurById(id);
		if (auteur == null) {
			throw new RessourceNotFoundException("Aucun auteur présent en base avec l'id : " + id);
		}
		return auteurMapper.toDto(auteur);

	}

	
	@Override
	@Transactional(readOnly = true)
	public List<LivreCompletDto> getLivresByAuteur(Auteur auteur) {
		// 1-> Je verifie d'abord que l'auteur passée en parammètre exitte bel et bien
		Auteur auteurTrouve = auteurDao.findAuteurById(auteur.getId());
		if (auteurTrouve == null) {
			throw new RessourceNotFoundException("Aucun auteur présent en base avec l'id : " + auteur.getId());
		} else {
			List<Livre> listeLivreByAuteur = auteurDao.findLivresByAuteur(auteur);
			if (listeLivreByAuteur.isEmpty()) {
				throw new NullRessourceException("Aucun livre écris par cette auteur : Nom de l'auteur = "
						+ auteur.getNom() + " " + auteur.getPrenom());
			}
			return livreMapper.toDtoListComplet(listeLivreByAuteur);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public AuteurDto getAuteurByNomPrenom(String nom, String prenom) {
		Auteur auteur = auteurDao.findAuteurByNomPrenom(nom, prenom);
		if (auteur == null) {
			throw new RessourceNotFoundException(
					"Aucun auteur présent en base avec le couple nom : " + nom + " et le prénom :" + prenom);
		}
		return auteurMapper.toDto(auteur);

	}

	@Transactional
	public void savedAuteur(AuteurRequest auteurRequest) {
		Auteur auteurTrouve= auteurDao.findAuteurByNomPrenom(auteurRequest.getNom(), auteurRequest.getPrenom());
		if (auteurTrouve!=null) {
			throw new RessourceExistException("Cet auteur est déjà présent en base avec ce nom : " + auteurRequest.getNom());
		}else {
		Auteur aut= new Auteur();
		aut.setBiographie(auteurRequest.getBiographie());
		aut.setNom(auteurRequest.getNom());
		aut.setPrenom(auteurRequest.getPrenom());
		auteurDao.savedAuteur(aut);
		}
		
	}

	@Override
	@Transactional
	public void updateAuteur(Long id ,AuteurRequest auteurRequest) {
		Auteur aut= auteurDao.findAuteurById(id);
		if (aut == null) {
			throw new RessourceNotFoundException("Aucun auteur présent en base avec l'id : " + id);
		}else {
		aut.setBiographie(auteurRequest.getBiographie());
		aut.setNom(auteurRequest.getNom());
		aut.setPrenom(auteurRequest.getPrenom());
		auteurDao.savedAuteur(aut);
		
		}
		
	}

	@Override
	@Transactional
	public int deleteAuteur(Long id) {
		Auteur auteur = auteurDao.findAuteurById(id);
		if (auteur == null) {
			throw new RessourceNotFoundException("Aucun auteur présent en base avec l'id : " + id);
		}
		auteurDao.deleteAuteur(id);
		return 1;
	}

}
