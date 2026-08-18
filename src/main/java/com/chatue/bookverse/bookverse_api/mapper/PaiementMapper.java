package com.chatue.bookverse.bookverse_api.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.chatue.bookverse.bookverse_api.dto.PaiementResponseDTO;
import com.chatue.bookverse.bookverse_api.entity.Paiement;

@Mapper(componentModel = "spring" , uses= {CommandeMapper.class})
public interface PaiementMapper {


	//Méthode qui transforme une entité en DTO -> donc en passe en parametre l'entité et l'objet qu'on retourne est le DTO
	@Mapping(target="commandeId" , source="commande.id")
	PaiementResponseDTO toDto(Paiement paiement);
	
	//Methode qui transforme une liste
	List<PaiementResponseDTO> toDtoList(List<Paiement> ListPaiement);
	
	@Mapping(target="commande" , source="commandeId")
	Paiement ToEntity(PaiementResponseDTO paiementResponseDTO);
}
