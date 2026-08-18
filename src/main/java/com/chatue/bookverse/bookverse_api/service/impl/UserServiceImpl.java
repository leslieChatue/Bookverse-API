package com.chatue.bookverse.bookverse_api.service.impl;

import java.util.List;

//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chatue.bookverse.bookverse_api.dao.UserDao;
import com.chatue.bookverse.bookverse_api.dto.UserResponseDTO;
import com.chatue.bookverse.bookverse_api.dto.request.UserRequest;
import com.chatue.bookverse.bookverse_api.entity.User;
import com.chatue.bookverse.bookverse_api.exception.RessourceExistException;
import com.chatue.bookverse.bookverse_api.exception.RessourceNotFoundException;
import com.chatue.bookverse.bookverse_api.mapper.UserMapper;
import com.chatue.bookverse.bookverse_api.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserDao userDao;
	private final UserMapper userMapper;
	//private final PasswordEncoder passwordEncoder;
	
	@Override
	@Transactional(readOnly = true)
	public UserResponseDTO getUserByEmail(String email) {
		return userMapper.toDto(userDao.findByEmail(email).orElseThrow(()-> new RessourceNotFoundException("Aucun utilisateur trouvé!")));
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserResponseDTO> getByNomContainingIgnoreCase(String nom) {
		return userMapper.toDtoList(userDao.findByUsernameContainingIgnoreCase(nom));
	}
	
	@Override
	@Transactional(readOnly = true)
	public UserResponseDTO getById(Long id) {
		return userMapper.toDto(userDao.findById(id).orElseThrow(() -> new RessourceNotFoundException("Aucun user trouvé avec cet Id")));
	}

	@SuppressWarnings("null")
	@Override
	@Transactional
	public void saveUser(UserRequest userRequest) {
			
		User user= userDao.findByEmail(userRequest.getEmail()).orElseThrow(() -> new RessourceNotFoundException("Aucun auteur présent en base avec ce nom ") );
		if (user != null) {
			throw new RessourceExistException("Cet utilisateur existe déjà en base " );
		}else {
		//	user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
			user.setEmail(userRequest.getEmail());
			user.setUsername(userRequest.getUsername());
			userDao.save(user);
	}
	}

	@Override
	public List<UserResponseDTO> getAllUsers() {
	
		return userMapper.toDtoList(userDao.findAll());
	}

	@Override
	public UserResponseDTO updateUserByUsername(Long id ,String username) {
	
		User user = userDao.findById(id).orElseThrow(() ->
		new RessourceNotFoundException("Aucun user trouvé avec cet id"+ id));
		user.setUsername(username);
		userDao.save(user);
		return userMapper.toDto(user);
	}

}
