package com.chatue.bookverse.bookverse_api.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "lignePanier")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class LignePanier implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	//A retirer 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "panier_id", nullable = false)
	private Panier panier;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "livre_id", nullable = false)
	private Livre livre;

	private Integer quantite;

}
