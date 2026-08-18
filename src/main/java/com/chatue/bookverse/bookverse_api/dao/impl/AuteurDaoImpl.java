package com.chatue.bookverse.bookverse_api.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import jakarta.persistence.criteria.Predicate;

import com.chatue.bookverse.bookverse_api.dao.AuteurDao;
import com.chatue.bookverse.bookverse_api.entity.Auteur;
import com.chatue.bookverse.bookverse_api.entity.Livre;
import com.chatue.bookverse.bookverse_api.exception.RessourceNotFoundException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class AuteurDaoImpl implements AuteurDao{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Auteur> findAllAuteurs() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Auteur> cq = cb.createQuery(Auteur.class);
		Root<Auteur> root = cq.from(Auteur.class);
		cq.select(root);	
		cq.orderBy(cb.asc(root.get("nom")));
		return entityManager.createQuery(cq).getResultList();
	}

	@Override
	public Optional<Auteur> findAuteurById(Long id) {
	try {
		CriteriaBuilder cb= entityManager.getCriteriaBuilder();
		CriteriaQuery<Auteur> cq =cb.createQuery(Auteur.class);
		Root<Auteur> root = cq.from(Auteur.class);
		Predicate predicates = cb.equal(root.get("id"), id);
		cq.select(root).where(predicates);
		return Optional.of(entityManager.createQuery(cq).getSingleResult());
	}catch (NoResultException e) {
		throw new RessourceNotFoundException("Auteur introuvable avec l'id :"+id);
	}
		
	}

	@Override
	public List<Livre> findLivresByAuteur(Auteur auteur) {
	
	    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<Livre> cq = cb.createQuery(Livre.class);
	    Root<Livre> root = cq.from(Livre.class);
	    Join<Livre, Auteur> joinAuteur = root.join("auteur");
	    Predicate condition = cb.equal(joinAuteur.get("id"), auteur.getId());
	    cq.select(root).where(condition);
	    cq.orderBy(cb.asc(root.get("titre")));
	    return entityManager.createQuery(cq).getResultList();
		
	}

	@Override
	public Optional<Auteur> findAuteurByNomPrenom(String nom, String prenom) {
		try {
	    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<Auteur> query = cb.createQuery(Auteur.class);
	    Root<Auteur> root = query.from(Auteur.class);
	    List<Predicate> conditions = new ArrayList<>();
	    // ✅ Comparaison correcte (utiliser isEmpty() ou equals, pas != "")
	    if(nom != null && !nom.isEmpty())
	        conditions.add(cb.like(root.get("nom"), nom));
	    if(prenom != null && !prenom.isEmpty())
	        conditions.add(cb.like(root.get("prenom"), prenom));
	    query.select(root).where(conditions.toArray(new Predicate[0]));
	    return Optional.of(entityManager.createQuery(query).getSingleResult());
		}catch (NoResultException e) {
			throw new RessourceNotFoundException("Auteur introuvable avec le couple nom :"+nom +" et le prénom :"+prenom);
		}
	}

	@Override
	public void savedAuteur(Auteur auteur) {
		entityManager.persist(auteur);		
	}

	@Override
	public void deleteAuteur(Long id) {
		entityManager.remove(findAuteurById(id));
	}

	@Override
	public boolean existsAuteurByNomPrenom(String nom, String prenom) {
		return findAuteurByNomPrenom(nom, prenom).isPresent();	
	}

}
