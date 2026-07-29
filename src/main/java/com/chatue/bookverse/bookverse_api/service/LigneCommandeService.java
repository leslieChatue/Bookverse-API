package com.chatue.bookverse.bookverse_api.service;

import java.util.List;

import com.chatue.bookverse.bookverse_api.dto.LigneCommandeDTO;

public interface LigneCommandeService {

	List<LigneCommandeDTO> getLigneCommandeByIdCommande(Long commandeId);

}
