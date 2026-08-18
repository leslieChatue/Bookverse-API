package com.chatue.bookverse.bookverse_api.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.chatue.bookverse.bookverse_api.dto.RoleDTO;
import com.chatue.bookverse.bookverse_api.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {

	RoleDTO toDto(Role role);
	List<RoleDTO> toDto(List<Role> role);
	Role toEntity(RoleDTO roleDTO);
	default Role toDtoByIdRole(Long id) {
        if (id == null) return null;
        Role role = new Role();
        role.setId(id);
        return role;
	}
}
