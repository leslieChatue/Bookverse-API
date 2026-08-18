package com.chatue.bookverse.bookverse_api.dto.request;



import com.chatue.bookverse.bookverse_api.utils.StrongPassword;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class UserRequest {
	
	@NotBlank(message = "Merci de saisir une valeur!")
	@Size(min=3 , max=50 , message="Merci d'entrer un username dont la taille est comprise entre 3 et 50")
	private String username;
	@NotBlank(message = "Merci de saisir une valeur!")
	@Size(min=3 , max=50 , message="Merci d'entrer un username dont la taille est comprise entre 3 et 50")
	@Email(message = "Merci de saisir une adresse mail correcte!")
	private String email;
	@NotBlank(message = "Merci de saisir une valeur!")
	@StrongPassword(message = "Le mot de passe doit contenir au moins 8 caractères, une majuscule, une minuscule, un chiffre et un caractère spécial." )
	private String password;
}
