package com.chatue.bookverse.bookverse_api.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "commande")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Commande implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name ="numero_commande",unique = true, nullable=false , updatable=false )
	private String numeroCommande;
	@Column(name = "date_commande",nullable=false)
	private LocalDateTime  dateCommande;
	@Enumerated(EnumType.STRING)
	@Column(name = "statut",nullable=false)
	private StatutCommande statut;
	@Column(name = "montant_total",nullable=false)
	private BigDecimal montantTotal;
	@Column( name = "user_id",nullable = false)
	private Long userId;
	}
