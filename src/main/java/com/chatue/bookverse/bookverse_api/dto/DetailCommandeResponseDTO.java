package com.chatue.bookverse.bookverse_api.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class DetailCommandeResponseDTO {


	  	private CommandeResponseDTO commandeDto;

	    private List<LigneCommandeDTO> ligneCommande;

}