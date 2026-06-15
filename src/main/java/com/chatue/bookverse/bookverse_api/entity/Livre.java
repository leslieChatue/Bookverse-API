package com.chatue.bookverse.bookverse_api.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;


/**
 * The persistent class for the livre database table.
 * 
 */
@Entity
@Table(name="livre")
@NamedQuery(name="Livre.findAll", query="SELECT l FROM Livre l")
@AllArgsConstructor
@NoArgsConstructor
public class Livre implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(name="date_creation", nullable=false)
	private Timestamp dateCreation;

	@Column(name="date_modification", nullable=false)
	private Timestamp dateModification;

	@Column(length=2147483647)
	private String description;

	@Column(nullable=false, length=20)
	private String isbn;

	@Column(nullable=false, precision=10, scale=2)
	private BigDecimal prix;

	@Column(nullable=false)
	private Integer stock;

	@Column(nullable=false, length=255)
	private String titre;

	//bi-directional many-to-one association to Auteur
	@ManyToOne
@JoinColumn(name="auteur_id", nullable=false)
	private Auteur auteur;

	//bi-directional many-to-one association to Categorie
	@ManyToOne
@JoinColumn(name="categorie_id", nullable=false)
	private Categorie categorie;
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getDateCreation() {
		return this.dateCreation;
	}

	public void setDateCreation(Timestamp dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Timestamp getDateModification() {
		return this.dateModification;
	}

	public void setDateModification(Timestamp dateModification) {
		this.dateModification = dateModification;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIsbn() {
		return this.isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public BigDecimal getPrix() {
		return this.prix;
	}

	public void setPrix(BigDecimal prix) {
		this.prix = prix;
	}

	public Integer getStock() {
		return this.stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getTitre() {
		return this.titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public Auteur getAuteur() {
		return this.auteur;
	}

	public void setAuteur(Auteur auteur) {
		this.auteur = auteur;
	}

	public Categorie getCategorie() {
		return this.categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	@Override
	public int hashCode() {
		return Objects.hash(auteur, categorie, dateCreation, dateModification, description, id, isbn, prix, stock,
				titre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Livre other = (Livre) obj;
		return Objects.equals(auteur, other.auteur) && Objects.equals(categorie, other.categorie)
				&& Objects.equals(dateCreation, other.dateCreation)
				&& Objects.equals(dateModification, other.dateModification)
				&& Objects.equals(description, other.description) && Objects.equals(id, other.id)
				&& Objects.equals(isbn, other.isbn) && Objects.equals(prix, other.prix)
				&& Objects.equals(stock, other.stock) && Objects.equals(titre, other.titre);
	}

	@Override
	public String toString() {
		return "Livre [id=" + id + ", dateCreation=" + dateCreation + ", dateModification=" + dateModification
				+ ", description=" + description + ", isbn=" + isbn + ", prix=" + prix + ", stock=" + stock + ", titre="
				+ titre + ", auteur=" + auteur + ", categorie=" + categorie + "]";
	}

	
}