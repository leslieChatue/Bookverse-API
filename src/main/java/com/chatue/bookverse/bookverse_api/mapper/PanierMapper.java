package com.chatue.bookverse.bookverse_api.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.chatue.bookverse.bookverse_api.dto.PanierResponseDTO;
import com.chatue.bookverse.bookverse_api.entity.LignePanier;
import com.chatue.bookverse.bookverse_api.entity.Panier;

@Mapper(componentModel = "spring", uses= {UserMapper.class, LignePanierMapper.class} )
public interface PanierMapper {


	//Méthode qui transforme une entité en DTO -> donc en passe en parametre l'entité et l'objet qu'on retourne est le DTO
	@Mapping(target = "userId", source = "user.id")
	PanierResponseDTO toDto(Panier panier);
	//Panier toEntity(PanierResponseDTO panierResponse);
	 default BigDecimal calculSousTotal(LignePanier lignePanier) {
	        return lignePanier.getLivre()
	                .getPrix()
	                .multiply(BigDecimal.valueOf(lignePanier.getQuantite()));
	    }
	
	//Methode qui transforme une liste
	List<PanierResponseDTO> toDtoList(List<Panier> listPanier);
	
	@Mapping(target="user" , source="userId")
	Panier toEntity(PanierResponseDTO panierResponse);
	
	default Panier toEntityLong(Long panierId) {
		if(panierId==null) return null;
		else {
			Panier panier= new Panier();
			panier.setId(panierId);
			return panier;
		}
	}
}
