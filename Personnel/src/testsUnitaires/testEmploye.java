package testsUnitaires;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

import personnel.DateInvalide;
import personnel.Employe;
import personnel.GestionPersonnel;
import personnel.Ligue;
import personnel.SauvegardeImpossible;


class testEmploye {
	GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();
	 
	
	//tests sur les formats de dates possibles
		@Test
		void testCreationEmploye() throws SauvegardeImpossible, DateInvalide
		{
			Ligue ligue1 = gestionPersonnel.addLigue("Ligue1");
			
			Employe employe1 = ligue1.addEmploye("nom", "prénom", "adresse@mail.com", "mdp", LocalDate.parse("2024-01-13"), LocalDate.parse("2025-01-13"));
			assertEquals(ligue1, employe1.getLigue());
			assertEquals("nom", employe1.getNom());
			assertEquals("prénom", employe1.getPrenom());
			assertEquals("adresse@mail.com", employe1.getMail());
			assertTrue(employe1.checkPassword("mdp"));
			assertEquals(LocalDate.parse("2024-01-13"), employe1.getDateArrivee());
			assertEquals(LocalDate.parse("2025-01-13"), employe1.getDateDepart());
			
			Employe employe2 = ligue1.addEmploye("nom2", "prénom2", "adresse2@mail.com", "mdp2", LocalDate.parse("2022-01-13"), LocalDate.parse("2023-01-13"));
			assertEquals(ligue1, employe2.getLigue());
			assertEquals("nom2", employe2.getNom());
			assertEquals("prénom2", employe2.getPrenom());
			assertEquals("adresse2@mail.com", employe2.getMail());
			assertTrue(employe2.checkPassword("mdp2"));
			assertEquals(LocalDate.parse("2022-01-13"), employe2.getDateArrivee());
			assertEquals(LocalDate.parse("2023-01-13"), employe2.getDateDepart());
			
			Ligue ligueA = gestionPersonnel.addLigue("Liga");
			
			Employe employeA = ligueA.addEmploye("lastname", "firstname", "mail@mail.com", "pwd", LocalDate.parse("2010-01-13"), LocalDate.parse("2011-01-13"));
			assertEquals(ligueA, employeA.getLigue());
			assertEquals("lastname", employeA.getNom());
			assertEquals("firstname", employeA.getPrenom());
			assertEquals("mail@mail.com", employeA.getMail());
			assertTrue(employeA.checkPassword("pwd"));
			assertEquals(LocalDate.parse("2010-01-13"), employeA.getDateArrivee());
			assertEquals(LocalDate.parse("2011-01-13"), employeA.getDateDepart());
			
			Employe employeB = ligueA.addEmploye("lastname2", "firstname2", "mail2@mail.com", "pwd2", LocalDate.parse("2012-01-13"), LocalDate.parse("2013-01-13"));
			assertEquals(ligueA, employeB.getLigue());
			assertEquals("lastname2", employeB.getNom());
			assertEquals("firstname2", employeB.getPrenom());
			assertEquals("mail2@mail.com", employeB.getMail());
			assertTrue(employeB.checkPassword("pwd2"));
			assertEquals(LocalDate.parse("2012-01-13"), employeB.getDateArrivee());
			assertEquals(LocalDate.parse("2013-01-13"), employeB.getDateDepart());
			
			
			// test format de date arrivée pas dans le bon format 
			assertThrows(DateTimeParseException.class, () -> {
				Employe goat = ligue1.addEmploye("Jude", "Belli", "heyJude@rm.com", "goal", null, LocalDate.parse("dat-invalide"));
		    }, "la date de départ doit être au format yyyy-mm-dd.");	
			
			assertThrows(DateInvalide.class, () -> {
				Employe goat = ligue1.addEmploye("Jude", "Belli", "heyJude@rm.com", "goal", LocalDate.parse("2024-01-13"), LocalDate.parse("2020-01-13"));
		    }, "la date de départ doit être postérieur a la date d'arrivée.");	
		}
	
	// test avec assertThrows sur le setteur dateArrivee
	@Test
	  void testsurSetDateArrivee() throws SauvegardeImpossible, DateInvalide{
		//creation d'empluyés 
		
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty", LocalDate.parse("2024-01-13"), LocalDate.parse("2024-01-14")); 
		assertEquals(LocalDate.parse("2024-01-13"), employe.getDateArrivee());
		// tester valeur dateDepart, nom, prénom, mdp, …
		
		employe = ligue.addEmploye("Boucharde", "Gérarde", "g.bouchard@gmail.come", "azertye", LocalDate.parse("2020-01-13"), LocalDate.parse("2022-01-14")); 
		assertEquals(LocalDate.parse("2020-01-13"), employe.getDateArrivee());
		// pareil
	        
		/* Ligue liga = gestionPersonnel.addLigue("Liga");	
		Employe goat = liga.addEmploye("Vini", "jr", "vini.jr@rm.com", "vini", LocalDate.parse("2020-10-1"),null);
		//test avec AssertThrows sur des dates à null
	    assertThrows(DateTimeParseException.class, () -> {
	    goat.setdateArrivee(LocalDate.parse("2020-10-10"));
	    assertEquals(LocalDate.parse("2020-10-10"), goat.getdateArrivee());
	    }, "Le test devrait fonctionner.");*/
	   }
	
    @Test
    void testSetdateArrivee_InvalidDate() throws SauvegardeImpossible {
        Ligue liga = gestionPersonnel.addLigue("Liga");

        // Test pour une date d'arrivée invalide
        assertThrows(DateTimeParseException.class, () -> {
            Employe goat = liga.addEmploye("Jude", "Belli", "heyJude@rm.com", "goal", LocalDate.parse("2021-05-0"), null);
            }, "La format de date d'arrivée doit être YYYY-MM-DD");
    }
    
	@Test 
	//TU sur la dateArrivee
	void testSetDateArrivee() throws SauvegardeImpossible, DateInvalide
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléches");
		Employe employe = ligue.addEmploye("Bouchar", "Gérar", "bouchard@gmail.com", "zerty", LocalDate.parse("2024-01-13"), LocalDate.parse("2024-01-14"));
		
		//test sur le setteur
		employe.setDateArrivee(LocalDate.parse("2020-10-10"));
		assertEquals(LocalDate.parse("2020-10-10"), employe.getDateArrivee());
		
		//le setteur marche bien
		employe.setDateArrivee(LocalDate.parse("2021-10-10"));
		assertEquals(LocalDate.parse("2021-10-10"), employe.getDateArrivee());
		
		// on peut mettre une date à null
		employe.setDateArrivee(null);
		assertEquals(null, employe.getDateArrivee());

		// cas ou la date n'est pas renseignée à la création de l'employe
		 employe = ligue.addEmploye("Bouchar", "Gérar", "bouchard@gmail.com", "zerty", null, null);
		 assertEquals(null, employe.getDateArrivee());
		//on peut bien modifier une date qui était à null
		 
		employe.setDateArrivee(LocalDate.parse("2022-01-08"));
    	 assertEquals(LocalDate.parse("2022-01-08"), employe.getDateArrivee());
	}
	
	// TESTS sur les Dates d'arrivée et de Depart
	
	@Test 
	// tests sur la date de départ
	void testSetDateDepart() throws SauvegardeImpossible, DateInvalide
	{
		Ligue ligue = gestionPersonnel.addLigue("Liga");
		Employe employe = ligue.addEmploye("Vinicius", "Junior", "vini.jr@gmail.com", "azerty", LocalDate.parse("2018-01-13"), null);
		
		// Cas #1 - date départ à null
		assertEquals(null, employe.getDateDepart());
		
		//test setteur : cas normal:
		employe.setDateDepart(LocalDate.parse("2024-04-13"));
		
		// tets setteur : cas date depart antérieure à date arrivee
		 assertThrows(DateInvalide.class, () -> {
				employe.setDateDepart(LocalDate.parse("2017-04-13"));
		 }, "La date d'arrivée ne doit pas être sup à celle de départ");
		 
		//on essaie de mettre une date de depart, alors qu'il n'y a pas de date d'arrivee
			
		 assertThrows(DateInvalide.class, () -> {
				employe.setDateArrivee(null);
				employe.setDateDepart(LocalDate.parse("2014-08-14"));
         }, "Il ne peut y avoir de date de depart sans date d'arrivee");
			 
			//la dernière date inscrite est bien celle qui est conservée
		assertEquals(LocalDate.parse("2024-04-13"), employe.getDateDepart());
			//on remet la date à null
		employe.setDateDepart(null);
		assertEquals(null, employe.getDateDepart());
		
		
		//on essaie une date de depart null et une quelconque date d'arrivee
		employe.setDateArrivee(LocalDate.parse("2012-12-12"));
		employe.setDateDepart(null);

		employe.setDateArrivee(LocalDate.parse("2012-12-12"));
		employe.setDateDepart(LocalDate.parse("2012-12-13"));
		assertEquals(LocalDate.parse("2012-12-13"), employe.getDateDepart());

		employe.setDateDepart(LocalDate.parse("2024-02-13"));
			
		assertEquals(LocalDate.parse("2024-02-13"), employe.getDateDepart());
		
		employe.setDateDepart(LocalDate.parse("2024-04-13"));
			
		assertEquals(LocalDate.parse("2024-04-13"), employe.getDateDepart());
	}	
	
	@Test
	void testSetNom() throws SauvegardeImpossible, DateInvalide {
		Ligue ligue = gestionPersonnel.addLigue("Ligue1");
		Employe employe = ligue.addEmploye("Vinicius", "Junior", "vini.jr@gmail.com", "azerty", null, null);
		
		employe.setNom("matuidi");
		assertEquals("matuidi", employe.getNom());
		
		employe.setNom("verratti");
		assertEquals("verratti", employe.getNom());
		
		employe.setNom(null);
		assertNull(employe.getNom());
	}
	
	@Test
	void testSetPrenom() throws SauvegardeImpossible, DateInvalide {
		Ligue ligue = gestionPersonnel.addLigue("Ligue1");
		Employe employe = ligue.addEmploye("Vinicius", "Junior", "vini.jr@gmail.com", "azerty", null, null);
		
		employe.setPrenom("blaise");
		assertEquals("blaise", employe.getPrenom());
		
		employe.setPrenom("marco");
		assertEquals("marco", employe.getPrenom());
		
		employe.setPrenom(null);
		assertNull(employe.getPrenom());
	}
	
	@Test
	void testSetMail() throws SauvegardeImpossible, DateInvalide {
		Ligue ligue = gestionPersonnel.addLigue("Liga");
		Employe employe = ligue.addEmploye("Vinicius", "Junior", "vini.jr@gmail.com", "azerty", null, null);
		
		employe.setMail("matuidi@mail.com");
		assertEquals("matuidi@mail.com", employe.getMail());
		
		employe.setMail("marco@mail.com");
		assertEquals("marco@mail.com", employe.getMail());
		
		employe.setMail(null);
		assertNull(employe.getMail());
	}
	
	@Test
	void testCheckPassword() throws SauvegardeImpossible, DateInvalide {
		Ligue ligue = gestionPersonnel.addLigue("Liga");
		Employe employe = ligue.addEmploye("Vinicius", "Junior", "vini.jr@gmail.com", "azerty", null, null);
		
		assertTrue(employe.checkPassword("azerty"));
		assertFalse(employe.checkPassword("azertyuiop"));
		
		employe.setPassword("azertyuiop");
		assertTrue(employe.checkPassword("azertyuiop"));
		assertFalse(employe.checkPassword("azerty"));
		
		employe.setPassword("supermdp");
		assertTrue(employe.checkPassword("supermdp"));
		assertFalse(employe.checkPassword("azerty"));
		assertFalse(employe.checkPassword("azertyuiop"));
	}
}


