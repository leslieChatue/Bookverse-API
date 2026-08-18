package com.chatue.bookverse.bookverse_api.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.chatue.bookverse.bookverse_api.entity.Categorie;
import com.chatue.bookverse.bookverse_api.entity.Livre;

@DataJpaTest
public class LivreDaoTest {

	@Autowired
	private  LivreDao livreDao;

	@BeforeEach
	public void  initialiser_livres() {
		Livre livre = new Livre();
		livre.setDateCreation(LocalDateTime.now());
		livre.setDescription("Livre fictif");
		livre.setId(1L);
		livre.setTitre("Titre fictif");
		livre.setPrix(new BigDecimal(12));
		try {
			livreDao.save(livre);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	@DisplayName("Enregistrer un livre")
	public void enregistrerLivre() {
		Livre livre = new Livre();
		livre.setDescription("Livre d'amour");
		livre.setCategorie(new Categorie(1L,"romance"));
		livreDao.save(livre);
		int tailleExpected=1;
		int result =livreDao.findAll().size();
		assertEquals(tailleExpected , result);
	}
	
	
}
