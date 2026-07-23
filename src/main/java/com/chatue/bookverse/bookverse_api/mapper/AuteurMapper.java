package com.chatue.bookverse.bookverse_api.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.chatue.bookverse.bookverse_api.dto.AuteurDto;
import com.chatue.bookverse.bookverse_api.entity.Auteur;

//AuteurMapper.java
@Mapper(componentModel = "spring")
public interface AuteurMapper {

	//Méthode qui transforme une entité en DTO -> donc en passe en parametre l'entité et l'objet qu'on retourne est le DTO
	AuteurDto toDto(Auteur auteur);
	//Méthode qui transforme un DTO en entité -> donc en passe en parametre le dto et l'objet qu'on retourne est l'entité
	Auteur toEntity(AuteurDto auteurResponse);
	//Methode qui transforme une liste
	List<AuteurDto> toDtoList(List<Auteur> ListAuteur);

	
}

