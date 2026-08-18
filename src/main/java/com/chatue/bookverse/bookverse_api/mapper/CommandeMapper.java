package com.chatue.bookverse.bookverse_api.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import com.chatue.bookverse.bookverse_api.dto.CommandeResponseDTO;
import com.chatue.bookverse.bookverse_api.entity.Commande;

//AuteurMapper.java
@Mapper(componentModel = "spring" , uses= {UserMapper.class} )
public interface CommandeMapper {

	//Méthode qui transforme une entité en DTO -> donc en passe en parametre l'entité et l'objet qu'on retourne est le DTO
	CommandeResponseDTO toDto(Commande commande);
	
	//Méthode qui transforme un DTO en entité -> donc en passe en parametre le dto et l'objet qu'on retourne est l'entité	
	Commande toEntity(CommandeResponseDTO commandeResponse);
	
	//Methode qui transforme une liste
	List<CommandeResponseDTO> toDtoList(List<Commande> listCommande);

	default Commande toEntintyLong(Long commandeId) {
		if(commandeId ==null) return null;
		else {
			Commande commande= Commande.builder().id(commandeId).build();
			return commande;
		}
	}
}

