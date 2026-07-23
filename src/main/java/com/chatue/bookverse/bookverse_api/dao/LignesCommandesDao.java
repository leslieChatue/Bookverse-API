package com.chatue.bookverse.bookverse_api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatue.bookverse.bookverse_api.entity.LignesCommandes;

public interface LignesCommandesDao extends JpaRepository<LignesCommandes, Long> {

List<LignesCommandes> findByCommandeId(Long commandeId);
}
