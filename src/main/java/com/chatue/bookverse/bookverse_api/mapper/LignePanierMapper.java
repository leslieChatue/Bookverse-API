package com.chatue.bookverse.bookverse_api.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.chatue.bookverse.bookverse_api.dto.LignePanierDTO;
import com.chatue.bookverse.bookverse_api.entity.LignePanier;

@Mapper(componentModel = "spring")
public interface LignePanierMapper {


	 	@Mapping(target = "livreId", source = "livre.id")
	    @Mapping(target = "titreLivre", source = "livre.titre")
	    @Mapping(target = "prixUnitaire", source = "livre.prix")
	    @Mapping(target = "sousTotal", expression = "java(calculSousTotal(lignePanier))")
	    LignePanierDTO toDto(LignePanier lignePanier);

	    default BigDecimal calculSousTotal(LignePanier lignePanier) {
	        return lignePanier.getLivre()
	                .getPrix()
	                .multiply(BigDecimal.valueOf(lignePanier.getQuantite()));
	    }
	//Methode qui transforme une liste
	List<LignePanierDTO> toDtoList(List<LignePanier> ListLignesPaniers);

	List<LignePanier> toEntity(List<LignePanierDTO> lignes);
}
