package com.chatue.bookverse.bookverse_api.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.chatue.bookverse.bookverse_api.dto.UserResponseDTO;
import com.chatue.bookverse.bookverse_api.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {


	//Méthode qui transforme une entité en DTO -> donc en passe en parametre l'entité et l'objet qu'on retourne est le DTO
	UserResponseDTO toDto(User user);
	//Méthode qui transforme un DTO en entité -> donc en passe en parametre le dto et l'objet qu'on retourne est l'entité
	//User toEntity(UserResponseDTO userResponse);
	//Methode qui transforme une liste
	List<UserResponseDTO> toDtoList(List<User> ListUser);
	User toEntity(UserResponseDTO byId);
}
