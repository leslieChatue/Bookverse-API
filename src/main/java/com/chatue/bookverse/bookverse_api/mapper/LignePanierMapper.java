package com.chatue.bookverse.bookverse_api.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.chatue.bookverse.bookverse_api.dto.LignePanierDTO;
import com.chatue.bookverse.bookverse_api.entity.LignesPaniers;

@Mapper(componentModel = "spring")
public interface LignePanierMapper {


	 	@Mapping(target = "livreId", source = "livre.id")
	    @Mapping(target = "titreLivre", source = "livre.titre")
	    @Mapping(target = "prixUnitaire", source = "livre.prix")
	    @Mapping(target = "sousTotal", expression = "java(calculSousTotal(lignePanier))")
	    LignePanierDTO toDto(LignesPaniers lignePanier);

	    default BigDecimal calculSousTotal(LignesPaniers lignePanier) {
	        return lignePanier.getLivre()
	                .getPrix()
	                .multiply(BigDecimal.valueOf(lignePanier.getQuantite()));
	    }
	//Methode qui transforme une liste
	List<LignePanierDTO> toDtoList(List<LignesPaniers> ListLignesPaniers);
}
