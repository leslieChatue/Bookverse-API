package com.chatue.bookverse.bookverse_api.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"id","nom"})
@Schema(description = "Dto de l'entité categorie")
public class CategorieDto {
	  @Schema(description="identifiant unique permettant d'idenifier une catégorie" , example = "1")
	  private Long id;
	  @Schema(description="nom permettant d'identifier un auteur" , example = "Dupont")
      private String nom;
	
}
