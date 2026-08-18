package com.chatue.bookverse.bookverse_api.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "paiement")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Paiement implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "commande_id",
        nullable = false,
        unique = true
    )
	private Commande commande;
	@Column(name="date_paiement", nullable=false , updatable=false)
	private LocalDateTime datePaiement;
	@Column(name="montant", nullable=false , updatable=false)
	private BigDecimal montant;
	@Column(name="mode_paiement", nullable=false , updatable=false)
	@Enumerated(EnumType.STRING)
	private ModePaiement modePaiement;
	@Column(name="statut_paiement", nullable=false , updatable=false)
	@Enumerated(EnumType.STRING)
	private StatutPaiement statutPaiement;
}
