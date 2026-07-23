package com.chatue.bookverse.bookverse_api.dto.request;



import com.chatue.bookverse.bookverse_api.entity.ModePaiement;

import jakarta.validation.constraints.NotBlank;
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
public class PaiementRequest {

	@NotBlank(message="Merci de bien vouloir saisir une valeur!")
    private Long commandeId;

	@NotBlank(message="Merci de bien vouloir saisir une valeur!")
    private ModePaiement modePaiement;

}
/*
dto

│
├── commande
│   ├── CommandeRequestDTO.java
│   ├
│   ├── ModifierStatutCommandeDTO.java
│   ├── 
│


*/