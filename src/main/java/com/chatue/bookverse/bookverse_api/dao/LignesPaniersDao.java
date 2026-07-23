package com.chatue.bookverse.bookverse_api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatue.bookverse.bookverse_api.entity.LignesPaniers;

public interface LignesPaniersDao extends JpaRepository<LignesPaniers, Long> {

	List<LignesPaniers> findByPanierId(Long panierId);
	
	void deleteByPanierIdAndLivreId(
			Long panierId,
			Long livreId
			);
}
