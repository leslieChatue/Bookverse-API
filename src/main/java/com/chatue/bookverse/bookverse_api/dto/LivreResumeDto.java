package com.chatue.bookverse.bookverse_api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"id","titre","description","prix","stock","auteur","categorie","dateCreation","dateModification"})
@Schema(description = "Dto de l'entité Livre recensent toutes les proprités de l'entité")
public class LivreResumeDto {
	@Schema(description="Identifiant unique permettant de retrouver un livre" , example="10")
	private Long id;
	@Schema(description="Titre d'un livre" , example="Jamais plus")
	private String titre;
	@Schema(description="Aucune idée de ce que c'est" , example="azsss")
	private String isbn;
	@Schema(description="Description du livre" , example="Livre poignant")
	private String description;
	@Schema(description="Prix du livre" , example="10,50")
	private BigDecimal prix;
	@Schema(description="Le nombre d'article en stock du livre" , example="40")
	private Integer stock;
	@Schema(description="Auteur qui a écrit le livre on le mappe avec le dto de la classe auteur" , example="Colleen Hoover")
	private String nomAuteur;
	@Schema(description="Categorie qui décrit dans quelle style d'écriture le livre se retrouve, on le mappe avec le dto de la classe" ,  example="Romance")
	private String nomCategorie;
	@Schema(description="Date de créattion du livre" , example="12-04-2020")
	private LocalDate dateCreation;
	@Schema(description="Date de modification du livre" , example="18-09-2024")
	private LocalDate dateModification;
}
