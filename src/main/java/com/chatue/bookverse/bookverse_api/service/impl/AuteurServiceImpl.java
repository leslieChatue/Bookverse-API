package com.chatue.bookverse.bookverse_api.service.impl;

import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chatue.bookverse.bookverse_api.dao.AuteurDao;
import com.chatue.bookverse.bookverse_api.dto.AuteurDto;
import com.chatue.bookverse.bookverse_api.dto.LivreCompletDto;
import com.chatue.bookverse.bookverse_api.dto.request.AuteurLivreRequest;
import com.chatue.bookverse.bookverse_api.dto.request.AuteurRequest;
import com.chatue.bookverse.bookverse_api.entity.Auteur;
import com.chatue.bookverse.bookverse_api.entity.Livre;
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
	@PreAuthorize("hasRole('USER')")
	public List<AuteurDto> getAllAuteurs() {
		List<Auteur> listeAuteurs = auteurDao.findAllAuteurs();
		return auteurMapper.toDtoList(listeAuteurs);
	}

	@Override
	@Transactional(readOnly = true)
	@PreAuthorize("hasRole('ADMIN')")
	public AuteurDto getAuteurById(Long id) {
		return auteurMapper.toDto(auteurDao.findAuteurById(id).orElseThrow(()->new RessourceNotFoundException("Aucun auteur présent en base avec l'id : " + id)));
	}

	
	@Override
	@Transactional(readOnly = true)
	@Secured("ADMIN")
	public List<LivreCompletDto> getLivresByAuteur(AuteurLivreRequest auteur) {
		// 1-> Je verifie d'abord que l'auteur passé en parammètre existe belle et bien
		String nomPrenom = auteur.getNom()+" "+auteur.getPrenom();
		Auteur auteurTrouve = auteurDao.findAuteurByNomPrenom(auteur.getNom(),auteur.getPrenom()).orElseThrow(()->new RessourceNotFoundException("Aucun auteur présent en base avec le couple nom prenom: " + nomPrenom ));
			List<Livre> listeLivreByAuteur = auteurDao.findLivresByAuteur(auteurTrouve);
			return livreMapper.toDtoListComplet(listeLivreByAuteur);
	}

	@Override
	@Transactional(readOnly = true)
	public AuteurDto getAuteurByNomPrenom(String nom, String prenom) {
		String nomPrenom = nom+" "+prenom;
		return	auteurMapper.toDto(auteurDao.findAuteurByNomPrenom(nom, prenom).orElseThrow(()->new RessourceNotFoundException("Aucun auteur présent en base avec le couple nom prenom: " + nomPrenom )));

	}

	@Transactional
	public AuteurDto savedAuteur(AuteurRequest auteurRequest) {
		String nomPrenom = auteurRequest.getNom()+" "+auteurRequest.getPrenom();
		if (auteurDao.existsAuteurByNomPrenom(auteurRequest.getNom(), auteurRequest.getPrenom())) {
			throw new RessourceExistException("Cet auteur est déjà présent en base avec ce nom : " + nomPrenom);
		}else {
		Auteur aut= new Auteur();
		aut.setBiographie(auteurRequest.getBiographie());
		aut.setNom(auteurRequest.getNom());
		aut.setPrenom(auteurRequest.getPrenom());
		auteurDao.savedAuteur(aut);
		return auteurMapper.toDto(aut);
		}
		
	}

	@Override
	@Transactional
	public AuteurDto updateAuteur(Long id ,AuteurRequest auteurRequest) {
		Auteur aut= auteurDao.findAuteurById(id).orElseThrow(()->new RessourceNotFoundException("Aucun auteur présent en base avec  cet id: " + id ));
		aut.setBiographie(auteurRequest.getBiographie());
		aut.setNom(auteurRequest.getNom());
		aut.setPrenom(auteurRequest.getPrenom());
		auteurDao.savedAuteur(aut);
		return auteurMapper.toDto(aut);
	}

	@Override
	@Transactional
	public void deleteAuteur(Long id) {
		auteurDao.findAuteurById(id).orElseThrow(()->new RessourceNotFoundException("Aucun auteur présent en base avec cet id: " + id ));
		auteurDao.deleteAuteur(id);
	}

}
