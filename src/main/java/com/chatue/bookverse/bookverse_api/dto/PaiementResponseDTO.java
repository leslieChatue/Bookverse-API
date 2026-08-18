package com.chatue.bookverse.bookverse_api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.chatue.bookverse.bookverse_api.entity.ModePaiement;
import com.chatue.bookverse.bookverse_api.entity.StatutPaiement;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
public class PaiementResponseDTO {


    private Long id;


    private Long commandeId;


    private BigDecimal montant;


    private ModePaiement modePaiement;


    private StatutPaiement statutPaiement;


    private LocalDateTime datePaiement;

}