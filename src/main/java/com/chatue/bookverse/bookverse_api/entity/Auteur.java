package com.chatue.bookverse.bookverse_api.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * The persistent class for the auteur database table.
 * 
 */
@Entity
@Table(name = "auteur")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Auteur implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(length = 2147483647)
	private String biographie;

	@Column(nullable = false, length = 255)
	private String nom;

	@Column(length = 255)
	private String prenom;

	

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

	@Override
	public int hashCode() {
		return Objects.hash(biographie, id, nom, prenom);
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
			 && Objects.equals(nom, other.nom)
				&& Objects.equals(prenom, other.prenom);
	}

	@Override
	public String toString() {
		return "Auteur [id=" + id + ", biographie=" + biographie + ", nom=" + nom + ", prenom=" + prenom + "]";
	}

}