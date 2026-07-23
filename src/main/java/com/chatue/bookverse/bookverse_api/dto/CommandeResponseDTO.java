package com.chatue.bookverse.bookverse_api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.chatue.bookverse.bookverse_api.entity.StatutCommande;
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
public class CommandeResponseDTO {


    private Long id;


    private String numeroCommande;


    private Long userId;


    private LocalDateTime dateCommande;


    private StatutCommande statut;


    private BigDecimal montantTotal;


   


}