package com.chatue.bookverse.bookverse_api.dto;

import java.math.BigDecimal;
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
public class LigneCommandeDTO {


    private Long id;


    private Long livreId;


    private String titreLivre;


    private Integer quantite;


    private BigDecimal prixUnitaire;


    private BigDecimal sousTotal;

}