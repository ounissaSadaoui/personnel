package testsUnitaires;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import personnel.*;

class testLigue 
{
	GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();
	
	
	@Test
	void createLigue() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		assertEquals("Fléchettes", ligue.getNom());
	}

	@Test
	void addEmploye() throws SauvegardeImpossible, DateInvalide
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty", LocalDate.parse("2024-01-13"), LocalDate.parse("2024-01-14")); 
		assertEquals(employe, ligue.getEmployes().first());
	}
	
	@Test
	void editLigue() throws SauvegardeImpossible{
		Ligue ligue = gestionPersonnel.addLigue("Ligue1");
		
		// change le nom de la ligue
		ligue.setNom("Ligue2");
		assertEquals("Ligue2", ligue.getNom());
		
	}
	
	@Test
	void testSetNom() throws SauvegardeImpossible {
		Ligue ligue = gestionPersonnel.addLigue("Ligue1");
		
		ligue.setNom("Liga");
		assertEquals("Liga", ligue.getNom());
		
		ligue.setNom("PremierLeague");
		assertEquals("PremierLeague", ligue.getNom());
		
		ligue.setNom(null);
		assertNull(ligue.getNom());
	}
	
	@Test
	void testSetAdministrateur() throws SauvegardeImpossible, DateInvalide {
		Ligue ligue = gestionPersonnel.addLigue("ligueA");
		Employe employe1 = ligue.addEmploye("Zinedine", "Zidane", "zizou@gmail.com", "azerty", null, null);
		
		ligue.setAdministrateur(employe1);
		assertEquals(employe1, ligue.getAdministrateur());
		
		Employe employe2 = ligue.addEmploye("Carlo", "Ancelotti", "carlo@gmail.com", "azertyuiop", null, null);
		
		ligue.setAdministrateur(employe2);
		assertEquals(employe2, ligue.getAdministrateur());
		
		ligue.setAdministrateur(gestionPersonnel.getRoot());
		assertEquals(gestionPersonnel.getRoot(), ligue.getAdministrateur());
		
		Ligue ligue2 = gestionPersonnel.addLigue("salut");
		Employe employeSalut = ligue2.addEmploye("Carlo", "Ancelotti", "carlo@gmail.com", "azertyuiop", null, null);
		
		assertThrows(DroitsInsuffisants.class, () -> {
			ligue.setAdministrateur(employeSalut);
		}, "L'administrateur ne peut pas provenir d'une autre ligue.");
		
	}
	
	
}
