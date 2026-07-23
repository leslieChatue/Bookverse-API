package com.chatue.bookverse.bookverse_api.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LivreRequest {

	@NotBlank(message = "Vous devez obligatoirement saisir un titre!")
	@Size(min = 2, max = 50, message = "Vous devez obligatoirement saisir un titre  de plus de 2 caractères!!")
	private String titre;

	@NotBlank
	private String isbn;
	@NotBlank(message = "Vous devez obligatoirement saisir une description!")
	@Size(min = 2, max = 100, message = "Vous devez obligatoirement saisir une description  de plus de 2 caractères!!")
	private String description;

	@Positive(message = "Vous devez obligatoirement saisir un prix d'une valeur supérieure ou égale à 0€!")
	@Min(value = 5, message = "Vous devez obligatoirement saisir un prix d'une valeur supérieure ou égale à 5€!")
	private BigDecimal prix;

	@PositiveOrZero
	private Integer stock;
	
	@NotBlank(message = "Vous devez obligatoirement saisir un auteur!")
	private Long auteurId;
	@NotBlank(message = "Vous devez obligatoirement saisir une categorie!")
	private Long categorieId;

}
