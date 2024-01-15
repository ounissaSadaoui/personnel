package testsUnitaires;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import personnel.DateInvalide;
import personnel.Employe;
import personnel.GestionPersonnel;
import personnel.Ligue;
import personnel.SauvegardeImpossible;

class testEmploye {
	GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();

	@Test
	void testCreationEmploye() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty", LocalDate.parse("2024-01-13"), LocalDate.parse("2024-01-14")); 
		assertEquals(LocalDate.parse("2024-01-13"), employe.getdateArrivee());
		// tester valeur dateDepart, nom, prénom, mdp, …
		
		employe = ligue.addEmploye("Boucharde", "Gérarde", "g.bouchard@gmail.come", "azertye", LocalDate.parse("2020-01-13"), LocalDate.parse("2022-01-14")); 
		assertEquals(LocalDate.parse("2020-01-13"), employe.getdateArrivee());
		// pareil
	
	}
	
	@Test 
	//TU sur la dateArrivee
	void testSetDateArrivee() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléches");
		Employe employe = ligue.addEmploye("Bouchar", "Gérar", "bouchard@gmail.com", "zerty", LocalDate.parse("2024-01-13"), LocalDate.parse("2024-01-14"));
		
		//test sur le setteur
		try {
			employe.setdateArrivee(LocalDate.parse("2020-10-10"));
			assertEquals(LocalDate.parse("2020-10-10"), employe.getdateArrivee());
		} catch (DateInvalide e) {
			// fail ("le set devrait marcher ):
		}
		
		//le setteur marche bien
		
		try {
			employe.setdateArrivee(LocalDate.parse("2021-10-10"));
			assertEquals(LocalDate.parse("2021-10-10"), employe.getdateArrivee());
		} catch (DateInvalide e) {
			// fail ("le set devrait marcher ):

		}
		
		// on peut mettre une date à null
		try {
			employe.setdateArrivee(null);
			assertEquals(null, employe.getdateArrivee());

		} catch (DateInvalide e) {
			//"c'était un test sur une date à null);
		}

		// cas ou la date n'est pas renseignée à la création de l'employe
		 employe = ligue.addEmploye("Bouchar", "Gérar", "bouchard@gmail.com", "zerty", null, null);
		 assertEquals(null, employe.getdateArrivee());
		//on peut bien modifier une date qui était à null
		 try {
			employe.setdateArrivee(LocalDate.parse("2022-01-08"));
			 assertEquals(LocalDate.parse("2022-01-08"), employe.getdateArrivee());

		} catch (DateInvalide e) {
			//fail("il ne devrait y avoir aucun problème);
		};


		
	}
	// TESTS sur les Dates d'arrivée et de Depart
	
	@Test 
	// tests sur la date de départ
	void testSetDateDepart() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Liga");
		Employe employe = ligue.addEmploye("Vinicius", "Junior", "vini.jr@gmail.com", "azerty", LocalDate.parse("2018-01-13"), null);
		
		// Cas #1 - date départ à null
		assertEquals(null, employe.getdateDepart());
		
		//test setteur : cas normal:
		try {
			employe.setdateDepart(LocalDate.parse("2024-04-13"));
		} catch (DateInvalide e) {
			fail("on ne devrait pas être ici ");
		}
		// tets setteur : cas date depart antérieure à date arrivee
		try {
			employe.setdateDepart(LocalDate.parse("2017-04-13"));
		} catch (DateInvalide e) {
			//la dernière date inscrite est bien celle qui est conservée
			assertEquals(LocalDate.parse("2024-04-13"), employe.getdateDepart());
			//fail("on devrait bien être ici ");
		}
		try {
			//on essaie de mettre une date de depart, alors qu'il n'y a pas de date d'arrivee
			employe.setdateArrivee(null);
			employe.setdateDepart(LocalDate.parse("2014-08-14"));
		}catch (DateInvalide e) {
			//la dernière date inscrite est bien celle qui est conservée
			assertEquals(LocalDate.parse("2024-04-13"), employe.getdateDepart());
			//fail("on devrait bien être ici ");
		}
		try {
			//on remet la date à null
			employe.setdateDepart(null);
			assertEquals(null, employe.getdateDepart());
		}catch (DateInvalide e) {
			// (on ne devrait pas arriver ici)

		}
		try {
			employe.setdateArrivee(LocalDate.parse("2012-12-12"));
			employe.setdateDepart(null);

		}catch (DateInvalide e) {
			//on ne devrai pas être ici non plus
		}
		try {
			employe.setdateArrivee(LocalDate.parse("2012-12-12"));
			employe.setdateDepart(LocalDate.parse("2012-12-13"));
			assertEquals(LocalDate.parse("2012-12-13"), employe.getdateDepart());

		}catch (DateInvalide e) {
			//on ne devrai pas être ici non plus
		}
		
		try {
			employe.setdateDepart(LocalDate.parse("2024-02-13"));
			
			assertEquals(LocalDate.parse("2024-02-13"), employe.getdateDepart());
			
			employe.setdateDepart(LocalDate.parse("2024-04-13"));
			
			assertEquals(LocalDate.parse("2024-04-13"), employe.getdateDepart());
		} catch (DateInvalide e) {
		  //	fail("Ce code ne doit pas échouer");
		} 
		
		
		
}
}


