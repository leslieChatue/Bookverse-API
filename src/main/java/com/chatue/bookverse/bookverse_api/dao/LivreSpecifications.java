package com.chatue.bookverse.bookverse_api.dao;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.chatue.bookverse.bookverse_api.entity.Livre;

public class LivreSpecifications {

	public static Specification<Livre> prixEntre(BigDecimal minPrix, BigDecimal maxPrix){
		
		return (root , query ,cb) ->{
			//Cas ou on a les deux valeurs 
			if(minPrix!=null && maxPrix!=null){
				return cb.between(root.get("prix"), minPrix, maxPrix);
			}
			
			//Cas ou on a que la valeur minimale
			if(minPrix!=null ){
				return cb.greaterThanOrEqualTo(root.get("prix"), minPrix);
			}
			//Cas ou on a que la valeur maximale
			if( maxPrix!=null ){
				return cb.lessThanOrEqualTo(root.get("prix"), maxPrix);
			}
			return null;
			
		};	
	}
	
	public static Specification<Livre> stockDispo(Boolean stockDispo){
		 
		return (root , query , cb) -> {
			if(stockDispo) {
				return cb.ge(root.get("stock"), 0);
			}
		
		return null ;
		};
	}
}
