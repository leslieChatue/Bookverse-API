package com.chatue.bookverse.bookverse_api.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;


/**
 * The persistent class for the auteur database table.
 * 
 */
@Entity
@Table(name="auteur")
@NamedQuery(name="Auteur.findAll", query="SELECT a FROM Auteur a")
@AllArgsConstructor
@NoArgsConstructor
public class Auteur implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(length=2147483647)
	private String biographie;

	@Column(nullable=false, length=255)
	private String nom;

	@Column(length=255)
	private String prenom;

	//bi-directional many-to-one association to Livre
	@OneToMany(mappedBy="auteur")
	private List<Livre> livres;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBiographie() {
		return this.biographie;
	}

	public void setBiographie(String biographie) {
		this.biographie = biographie;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return this.prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public List<Livre> getLivres() {
		return this.livres;
	}

	public void setLivres(List<Livre> livres) {
		this.livres = livres;
	}

	public Livre addLivre(Livre livre) {
		getLivres().add(livre);
		livre.setAuteur(this);

		return livre;
	}

	public Livre removeLivre(Livre livre) {
		getLivres().remove(livre);
		livre.setAuteur(null);

		return livre;
	}

	@Override
	public int hashCode() {
		return Objects.hash(biographie, id, livres, nom, prenom);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Auteur other = (Auteur) obj;
		return Objects.equals(biographie, other.biographie) && Objects.equals(id, other.id)
				&& Objects.equals(livres, other.livres) && Objects.equals(nom, other.nom)
				&& Objects.equals(prenom, other.prenom);
	}

	@Override
	public String toString() {
		return "Auteur [id=" + id + ", biographie=" + biographie + ", nom=" + nom + ", prenom=" + prenom + ", livres="
				+ livres + "]";
	}

}