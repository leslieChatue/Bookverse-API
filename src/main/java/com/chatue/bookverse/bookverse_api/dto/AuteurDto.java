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
@JsonPropertyOrder({"id","nom","prenom","biographie"})
@Schema(description = "Dto de l'entité auteur")
public class AuteurDto {

	@Schema(description="identifiant unique permettant d'idenifier un auteur" , example = "1")
	private Long id;
	@Schema(description="nom permettant d'identifier un auteur" , example = "Dupont")
	private String nom;
	@Schema(description="prénom permettant d'identifier un auteur" , example = "Jean")
	private String prenom;
	@Schema(description="Présentation détaillée permettant d'identifier un auteur" , example = "1")
	private String biographie;
}
