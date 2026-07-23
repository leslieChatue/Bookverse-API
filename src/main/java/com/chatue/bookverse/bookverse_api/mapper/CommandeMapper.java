package com.chatue.bookverse.bookverse_api.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.chatue.bookverse.bookverse_api.dto.CommandeResponseDTO;
import com.chatue.bookverse.bookverse_api.entity.Commande;

//AuteurMapper.java
@Mapper(componentModel = "spring" , uses= {UserMapper.class} )
public interface CommandeMapper {

	//Méthode qui transforme une entité en DTO -> donc en passe en parametre l'entité et l'objet qu'on retourne est le DTO
	@Mapping(target = "userId" , source="utilisateur.id")
	CommandeResponseDTO toDto(Commande commande);
	//Méthode qui transforme un DTO en entité -> donc en passe en parametre le dto et l'objet qu'on retourne est l'entité
	@Mapping(target="utilisateur" , ignore = true)
	Commande toEntity(CommandeResponseDTO commandeResponse);
	//Methode qui transforme une liste
	@Mapping(target = "userId" , source="utilisateur.id")
	List<CommandeResponseDTO> toDtoList(List<Commande> ListCommande);

	
}

