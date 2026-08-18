package com.chatue.bookverse.bookverse_api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chatue.bookverse.bookverse_api.entity.LigneCommande;

@Repository
public interface LigneCommandeDao extends JpaRepository<LigneCommande, Long> {

List<LigneCommande> findByCommandeId(Long commandeId);
}
