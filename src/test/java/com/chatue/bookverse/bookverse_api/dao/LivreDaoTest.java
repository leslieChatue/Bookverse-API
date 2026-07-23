package com.chatue.bookverse.bookverse_api.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.math.BigDecimal;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.chatue.bookverse.bookverse_api.entity.Livre;
import com.chatue.bookverse.bookverse_api.exception.NullRessourceException;

@DataJpaTest
public class LivreDaoTest {

	@Autowired
	private  LivreDao livreDao;

	@BeforeEach
	public void  initialiser_livres() {
		Livre livre = new Livre();
		livre.setDateCreation(new Date());
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
		Livre livre1 = new Livre();
		livre1 = livreDao.findById(1L).orElseThrow(()-> new NullRessourceException("Aucune ressource trouvée"));
		assertEquals(1, livreDao.findAll().size());
	}
	
	
}
