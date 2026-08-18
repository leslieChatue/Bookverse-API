package com.chatue.bookverse.bookverse_api.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.chatue.bookverse.bookverse_api.entity.Categorie;
import com.chatue.bookverse.bookverse_api.entity.Livre;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface CategorieDao extends JpaRepository<Categorie, Long>{
	
	//JPQL
	@Query("SELECT c FROM Categorie c ORDER BY c.nom")
	List<Categorie> findAllCategories();

	@Query("SELECT c FROM Categorie c where c.id = :id")
    Optional<Categorie> findCategorieById(@Param("id") Long id);
	
	@Query("SELECT c FROM Categorie c where c.nom = :nom")
	Optional<Categorie> findCategorieByNom(@Param("nom") String nom);
	
	@Modifying
	@Query("DELETE FROM Categorie c WHERE c.id = :id")
	void deleteCategorieById(@Param("nom") Long id);
	
	//SQL NATIF
	@Query(value="Select * from livre l inner join categorie c on l.categorie_id=l.id where c.id = :categorieId", nativeQuery = true)
	List<Livre> findLivresByCategorie(Long categorieId);
	
	

}
