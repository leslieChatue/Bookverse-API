package com.chatue.bookverse.bookverse_api.dto.request;



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
	private String email;
}
