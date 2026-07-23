package com.chatue.bookverse.bookverse_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategorieRequest {
	 @NotBlank(message="Vous devez obligatoirement saisir un nom!")
	 @Size(min=2 , max=50,message="Vous devez obligatoirement saisir un nom  de plus de 2 caractères!!")
     private String nom;
}
