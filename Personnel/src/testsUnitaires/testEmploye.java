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
	//tests sur les formats de dates possibles
	@Test
	void testCreationEmploye() throws SauvegardeImpossible, DateInvalide
	{
        // test format de date arrivée pas dans le bon format 
		 Ligue liga = gestionPersonnel.addLigue("Liga");	
 		 assertThrows(DateTimeParseException.class, () -> {
 	 	     Employe goat = liga.addEmploye("Jude", "Belli", "heyJude@rm.com", "goal", null, LocalDate.parse("dat-invalide"));
	        }, "la date de départ doit ẑtre au format yyyy-mm-dd.");	
	}
	
	@Test 
	//TU sur la dateArrivee
	void testSetDateArrivee() throws SauvegardeImpossible, DateInvalide
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléches");
		Employe employe = ligue.addEmploye("Bouchar", "Gérar", "bouchard@gmail.com", "zerty", LocalDate.parse("2024-01-13"), LocalDate.parse("2024-01-14"));
		
		//test sur le setteur
		try {
			employe.setDateArrivee(LocalDate.parse("2020-10-10"));
			assertEquals(LocalDate.parse("2020-10-10"), employe.getDateArrivee());
		} catch (DateInvalide e) {}
		
		//le setteur marche bien
		
		try {
			employe.setDateArrivee(LocalDate.parse("2021-10-10"));
			assertEquals(LocalDate.parse("2021-10-10"), employe.getDateArrivee());
		} catch (DateInvalide e) {}
		
		// on peut mettre une date à null
		try {
			employe.setDateArrivee(null);
			assertEquals(null, employe.getDateArrivee());

		} catch (DateInvalide e) {}	
		
		// cas ou la date n'est pas renseignée à la création de l'employe
		 employe = ligue.addEmploye("Bouchar", "Gérar", "bouchard@gmail.com", "zerty", null, null);
		 assertEquals(null, employe.getDateArrivee());
		//on peut bien modifier une date qui était à null
		 
		 try {
			employe.setDateArrivee(LocalDate.parse("2022-01-08"));
			 assertEquals(LocalDate.parse("2022-01-08"), employe.getDateArrivee());

		  } catch (DateInvalide e) {};
		
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
		try {
			employe.setDateDepart(LocalDate.parse("2024-04-13"));
		} catch (DateInvalide e) {
			fail("on ne devrait pas être ici ");
		}

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
		try {
			//on remet la date à null
			employe.setDateDepart(null);
			assertEquals(null, employe.getDateDepart());
		}catch (DateInvalide e) {		}
		
		
		//on essaie une date de depart null et une quelconque date d'arrivee
		try {
			employe.setDateArrivee(LocalDate.parse("2012-12-12"));
			employe.setDateDepart(null);

		}catch (DateInvalide e) {}
		
		try {
			employe.setDateArrivee(LocalDate.parse("2012-12-12"));
			employe.setDateDepart(LocalDate.parse("2012-12-13"));
			assertEquals(LocalDate.parse("2012-12-13"), employe.getDateDepart());

		}catch (DateInvalide e) {}
		
		try {
			employe.setDateDepart(LocalDate.parse("2024-02-13"));
			
			assertEquals(LocalDate.parse("2024-02-13"), employe.getDateDepart());
			
			employe.setDateDepart(LocalDate.parse("2024-04-13"));
			
			assertEquals(LocalDate.parse("2024-04-13"), employe.getDateDepart());
		} catch (DateInvalide e) {} 
		
}
}


