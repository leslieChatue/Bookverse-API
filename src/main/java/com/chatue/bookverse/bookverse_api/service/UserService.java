package com.chatue.bookverse.bookverse_api.service;

import java.util.List;
import com.chatue.bookverse.bookverse_api.dto.UserResponseDTO;
import com.chatue.bookverse.bookverse_api.dto.request.UserRequest;

public interface UserService {
	
	UserResponseDTO getUserByEmail(String email);


	//boolean existsByEmail(String email);


	List<UserResponseDTO> getByNomContainingIgnoreCase(String nom);

	List<UserResponseDTO> getAllUsers();
	
	UserResponseDTO getById(Long id);


	void saveUser(UserRequest userRequest);


	UserResponseDTO updateUserByUsername(Long id , String username);
}
