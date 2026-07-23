package com.chatue.bookverse.bookverse_api.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import java.util.Objects;

/**
 * The persistent class for the categorie database table.
 * 
 */

@Table(name = "categorie")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Categorie implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(nullable = false, length = 100)
	private String nom;

	

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(id, nom);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categorie other = (Categorie) obj;
		return Objects.equals(id, other.id)  && Objects.equals(nom, other.nom);
	}

	@Override
	public String toString() {
		return "Categorie [id=" + id + ", nom=" + nom +  "]";
	}

}