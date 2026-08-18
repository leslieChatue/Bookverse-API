package com.chatue.bookverse.bookverse_api.dto;

import java.time.LocalDate;
import java.util.List;

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
public class PanierResponseDTO {


    private Long id;


    private Long userId;


    private List<LignePanierDTO> lignePanier;

    private LocalDate dateCreation;

  //  private BigDecimal montantTotal;

}