package com.chatue.bookverse.bookverse_api.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.chatue.bookverse.bookverse_api.entity.Livre;

@Repository
public interface LivreDao extends JpaRepository<Livre, Long> , JpaSpecificationExecutor<Livre> {

	//Implémentation de méthodes natives de Spring Data
		
	List<Livre> findByTitreContainingIgnoreCase(String titre);
		
	List<Livre> findByCategorieNomStartingWith(String nomCategorie);
	
	boolean existsByIsbn(String isbn);
	
	List<Livre> findByAuteurId(Long auteurId);
	
	List<Livre> findByCategorieId(Long categorieId);
	
	
}
