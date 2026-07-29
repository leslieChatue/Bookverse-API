package com.chatue.bookverse.bookverse_api.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.chatue.bookverse.bookverse_api.dto.LigneCommandeDTO;
import com.chatue.bookverse.bookverse_api.entity.LigneCommande;

@Mapper(componentModel = "spring" , uses= {LivreMapper.class})
public interface LigneCommandeMapper {


	//Méthode qui transforme une entité en DTO -> donc en passe en parametre l'entité et l'objet qu'on retourne est le DTO
	@Mapping(target="livreId" , source="livre.id")
	@Mapping(target="titreLivre", source="livre.titre")
	@Mapping(target = "sousTotal", expression = "java(calculSousTotal(ligneCommande))")
	LigneCommandeDTO toDto(LigneCommande ligneCommande);

	    default BigDecimal calculSousTotal(LigneCommande ligneCommande) {
	        return ligneCommande.getLivre()
	                .getPrix()
	                .multiply(BigDecimal.valueOf(ligneCommande.getQuantite()));
	    }
	//Methode qui transforme une liste
	List<LigneCommandeDTO> toDtoList(List<LigneCommande> ListLigneCommandes);
}
