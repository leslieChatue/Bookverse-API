package com.chatue.bookverse.bookverse_api.dao;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.chatue.bookverse.bookverse_api.entity.Livre;

public interface LivreDao extends JpaRepository<Livre, Long> {

	//Implémentation de méthodes natives de Spring Data
	
	List<Livre> findAll();
	
	List<Livre> findAllLivresByTitreContainingIgnoreCase(String titre);
		
	List<Livre> findByCategorieNomStartingWith(String nomCategorie);
	
	boolean existsByIsbn(String isbn);
	
	List<Livre> findByAuteurId(Long auteurId);
	
	List<Livre> findByCategorieId(Long categorieId);
	//Afficher uniquement les livres disponibles
	List<Livre> findByStockGreaterThan(Integer stock);
	
	List<Livre> findByPrixBetween(
			BigDecimal min,
			BigDecimal max
			);

}
