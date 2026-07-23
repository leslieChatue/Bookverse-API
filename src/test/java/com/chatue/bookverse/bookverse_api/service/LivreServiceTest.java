package com.chatue.bookverse.bookverse_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Executable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.chatue.bookverse.bookverse_api.dao.LivreDao;
import com.chatue.bookverse.bookverse_api.entity.Livre;
import com.chatue.bookverse.bookverse_api.exception.NullRessourceException;
import com.chatue.bookverse.bookverse_api.service.impl.LivreServiceImpl;

@ExtendWith(MockitoExtension.class)
public class LivreServiceTest {
	@Mock //Mok est un objet fictif il ne sticke rien n'enregistre rien et ne lit rien , c'est nous qui lui donnons un comportement pra défaut
	LivreDao livreDao;
	
	@InjectMocks //C'est l'objet réel sur lequel on pourra faire des modifications
	LivreServiceImpl livreService;
	//Variable globale
	List<Livre> listeLivres = new ArrayList<Livre>();
	Livre livre = new Livre();
	
	@BeforeAll
	public static void  initialiser_livres() {
		try {
		livre.setDateCreation(new Date());
		livre.setDescription("Livre fictif");
		livre.setId(1L);
		livre.setTitre("Titre fictif");
		livre.setPrix(new BigDecimal(12));
		

		listeLivres.add(livre);
		listeLivres.add(livre);
		listeLivres.add(livre);
		
			livreDao.save(livre);
			for(Livre l : listeLivres) {
				livreDao.save(l);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(listeLivres.size());
	}

	@Test
	@DisplayName("Test de l'enregistrement d'un livre")
	//@Disabled //permet de desactiver temporairement un test
	public void EnregistrerLivreTest() {
		String titre ="La case de papel";
		Livre livre = new Livre();
		livre.setTitre(titre);
		livre.setDescription("Inspirés d'une série d'aventure très populaire");
		
		//When n'est utilisé que sur l'objet mocké donc sur l'objet qui porte l'annotation @Mock
		//Verify n'est utilisé que sur l'objet mocké donc sur l'objet qui porte l'annotation @Mock
		//On mock les dépendances de la classe qu'on veut tester
		
		when(livreDao.save(livre)).thenReturn(livre);
		livreService.savedLivre(livre);
		assertNotNull(livre);
		assertEquals( "La case de papel",livre.getTitre());
		 verify(livreDao ,times(1)).save(livre);

	}
	
	@Test
	@DisplayName("Enregistrement d'un livre null pour s'assurer de verifier le comportement du retour de  nos exceptions")
	@Tag("unitaire") //permet de referencer la liste des tests que l'on veut executer en console maven : mvn test -Dgroups=unitaire
	public void test_de_l_ajout_dun_livre_null_return_exception() {
		Livre livre =null;
		//NullRessourceException nullException =	assertThrows(NullRessourceException.class ,() -> livreService.savedLivre(livre));
		//assertEquals("Impossible d'enregistrer ce livre car il est nul", nullException.getMessage());
		assertThrows(NullRessourceException.class ,() -> livreService.savedLivre(livre) , "Impossible d'ajouter ce livre car il est nul dès le départ");

	}
	
	
	//test pour la recherhe d'un livre par nom du titre 
		@Test
		@DisplayName("Methode qui permet de tester la méthode de recherche d'un livre par titre")
		@Tag("unitaires")
		public void test_afficher_livre_par_noms() {
			//findAllLivresByTitreContaining
			String titre = "Titre fictif";
			Livre livre = new Livre();
			when(livreDao.findAllLivresByTitreContaining(titre)).thenReturn(listeLivres);
			verify(livreDao,times(1)).findAllLivresByTitreContaining(titre);
			assertEquals(3, listeLivres.size(), "Test réussi");
			//return listeLivres.size()+"";
		}
}
