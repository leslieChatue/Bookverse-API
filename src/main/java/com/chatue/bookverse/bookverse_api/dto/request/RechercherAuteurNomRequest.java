package com.chatue.bookverse.bookverse_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor @ToString
public class RechercherAuteurNomRequest {
	 @NotBlank(message="Vous devez obligatoirement saisir un nom!")
	 @Size(min=2 , max=50,message="Vous devez obligatoirement saisir un nom  de plus de 2 caractères!!")
     private String nom;
	 @NotBlank(message="Vous devez obligatoirement saisir un prénom!")
	 @Size(min=2 , max=50,message="Vous devez obligatoirement saisir un prénom de plus de 2 caractères!")
	 private String prenom;
}
