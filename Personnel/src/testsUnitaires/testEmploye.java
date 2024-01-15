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
	void testSetDateArrivee() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty", LocalDate.parse("2024-01-13"), LocalDate.parse("2024-01-14"));
		
		employe.setdateArrivee(LocalDate.parse("2020-10-10"));
		
		assertEquals(LocalDate.parse("2020-10-10"), employe.getdateArrivee());
		
		employe.setdateArrivee(LocalDate.parse("2021-10-10"));
		
		assertEquals(LocalDate.parse("2021-10-10"), employe.getdateArrivee());
	}
	
	@Test
	void testSetDateDepart() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty", LocalDate.parse("2024-01-13"), LocalDate.parse("2024-01-14"));
		
		// Cas #1 - Tout est bon, départ après arrivée
		try {
			employe.setdateDepart(LocalDate.parse("2024-02-13"));
			
			assertEquals(LocalDate.parse("2024-02-13"), employe.getdateDepart());
			
			employe.setdateDepart(LocalDate.parse("2024-04-13"));
			
			assertEquals(LocalDate.parse("2024-04-13"), employe.getdateDepart());
		} catch (DateInvalide e) {
			fail("Ce code ne doit pas échouer");
		}
		
		// Cas #2 - KO, date départ antérieur à date arrivée
		try {
			employe.setdateDepart(LocalDate.parse("2020-10-10"));
			
			fail("Ce code DOIT échouer");
		} catch (DateInvalide e) {
			// DateInvalide comme prévu, on vérifie que la dateDepart n'ait pas changé
			assertEquals(LocalDate.parse("2024-04-13"), employe.getdateDepart());
		}
	}
}


