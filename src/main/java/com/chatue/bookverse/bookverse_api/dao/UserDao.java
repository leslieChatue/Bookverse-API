package com.chatue.bookverse.bookverse_api.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chatue.bookverse.bookverse_api.entity.User;
@Repository
public interface UserDao extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);


	boolean existsByEmail(String email);


	List<User> findByNomContainingIgnoreCase(String nom);
}
