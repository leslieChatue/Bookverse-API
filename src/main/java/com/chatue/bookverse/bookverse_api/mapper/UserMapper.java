package com.chatue.bookverse.bookverse_api.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.chatue.bookverse.bookverse_api.dto.CategorieDto;
import com.chatue.bookverse.bookverse_api.entity.Categorie;

@Mapper(componentModel = "spring")
public interface UserMapper {


	//Méthode qui transforme une entité en DTO -> donc en passe en parametre l'entité et l'objet qu'on retourne est le DTO
	CategorieDto toDto(Categorie categorie);
	//Méthode qui transforme un DTO en entité -> donc en passe en parametre le dto et l'objet qu'on retourne est l'entité
	Categorie toEntity(CategorieDto categorieResponse);
	//Methode qui transforme une liste
	List<CategorieDto> toDtoList(List<Categorie> ListCategorie);
}
