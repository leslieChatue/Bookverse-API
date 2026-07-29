package com.chatue.bookverse.bookverse_api.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * The persistent class for the livre database table.
 * 
 */
@Entity
@Table(name = "livre")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Livre implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(name = "date_creation", nullable = false)
	private LocalDateTime dateCreation;

	@Column(name = "date_modification", nullable = false)
	private LocalDateTime dateModification;

	@Column(length = 2147483647)
	private String description;

	@Column(nullable = false, length = 20)
	private String isbn;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal prix;

	@Column(nullable = false)
	private Integer stock;

	@Column(nullable = false, length = 255)
	private String titre;

	// bi-directional many-to-one association to Auteur
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "auteur_id", nullable = false)
	private Auteur auteur;

	// bi-directional many-to-one association to Categorie
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "categorie_id", nullable = false)
	private Categorie categorie;
	
	


}