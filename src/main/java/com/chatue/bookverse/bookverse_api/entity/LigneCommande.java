package com.chatue.bookverse.bookverse_api.entity;
import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ligneCommande")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LigneCommande implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="livre_id")
	private Livre livre ;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="commande_id")
	private Commande commande;
	
	@Column(name="quantite" , nullable=false)
	private int quantite;
	@Column(name="prix" , nullable=false)
	private BigDecimal prixUnitaire;
}
