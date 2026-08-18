package com.chatue.bookverse.bookverse_api.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.chatue.bookverse.bookverse_api.dto.UserResponseDTO;
import com.chatue.bookverse.bookverse_api.entity.User;

@Mapper(componentModel = "spring",uses = {RoleMapper.class})
public interface UserMapper {


	//Méthode qui transforme une entité en DTO -> donc en passe en parametre l'entité et l'objet qu'on retourne est le DTO
	@Mapping(target = "roleId", source = "roles.id")
	UserResponseDTO toDto(User user);
	
	//Méthode qui transforme un DTO en entité -> donc en passe en parametre le dto et l'objet qu'on retourne est l'entité
	@Mapping(target = "roles", source="roleId")
	@Mapping(target = "password", ignore = true) // Ignore the roles field when mapping from DTO to entity
	User toEntity(UserResponseDTO userResponse);
	
	//Methode qui transforme une liste
	List<UserResponseDTO> toDtoList(List<User> listUser);
	
	default User toEntityLong(Long idUser) {
		if(idUser==null) return null;
		else {
			User user= new User();
			user.setId(idUser);
			return user;
		}
	}

}
