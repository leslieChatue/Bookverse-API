package com.chatue.bookverse.bookverse_api.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "AuteurLivreRequest", description = "payload pour l'affichage de tous les livres d'un auteur")
public class AuteurLivreRequest {
	 @NotBlank(message="Vous devez obligatoirement saisir un nom!")
	 @Size(min=2 , max=50,message="Vous devez obligatoirement saisir un nom  de plus de 2 caractères!!")
	 @Schema(description = "Nom de l'auteur", example = "DUPONT", requiredMode = Schema.RequiredMode.REQUIRED)
     private String nom;
	 
	 @NotBlank(message="Vous devez obligatoirement saisir un prénom!")
	 @Size(min=2 , max=50,message="Vous devez obligatoirement saisir un prénom de plus de 2 caractères!")
	 @Schema(description = "Prénom de l'auteur", example = "SERGE", requiredMode = Schema.RequiredMode.REQUIRED)
	 private String prenom;
}
