package com.chatue.bookverse.bookverse_api.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "panier")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Panier {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id" , nullable=false)
	private User user;
	@Column(name="date_creation", nullable=false)
	private LocalDate dateCreation;
	@OneToMany(fetch = FetchType.LAZY , mappedBy = "panier", cascade = CascadeType.ALL ,orphanRemoval = true)
	private List<LignePanier> lignePanier;

}
