package com.chatue.bookverse.bookverse_api.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.chatue.bookverse.bookverse_api.dto.PanierResponseDTO;
import com.chatue.bookverse.bookverse_api.entity.Panier;

@Mapper(componentModel = "spring")
public interface PanierMapper {


	//Méthode qui transforme une entité en DTO -> donc en passe en parametre l'entité et l'objet qu'on retourne est le DTO
	@Mapping(target="userId" , source="utilisateur.id")
	PanierResponseDTO toDto(Panier panier);
	//Panier toEntity(PanierResponseDTO panierResponse);
	//Methode qui transforme une liste
	List<PanierResponseDTO> toDtoList(List<Panier> ListPanier);
	
	Panier toEntity(PanierResponseDTO panierResponse);
}
