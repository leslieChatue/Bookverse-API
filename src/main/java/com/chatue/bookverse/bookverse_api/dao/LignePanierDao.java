package com.chatue.bookverse.bookverse_api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chatue.bookverse.bookverse_api.entity.LignePanier;

@Repository
public interface LignePanierDao extends JpaRepository<LignePanier, Long> {

	List<LignePanier> findByPanierId(Long panierId);
	
	void deleteByPanierIdAndLivreId(
			Long panierId,
			Long livreId
			);
}
