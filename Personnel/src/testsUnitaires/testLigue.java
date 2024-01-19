package testsUnitaires;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import personnel.*;

class testLigue 
{
	GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();
	
	
	@Test //creation d'une ligue
	void createLigue() throws SauvegardeImpossible, DateInvalide
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		assertEquals("Fléchettes", ligue.getNom());
		
		// test sur les caractères spéciaux dans le nom
		Ligue spn = gestionPersonnel.addLigue("Supernatural 1 to :15");
		assertEquals("Supernatural 1 to :15", spn.getNom());
		//re test
		
		assertThrows(SauvegardeImpossible.class, () -> {
			Ligue lig = gestionPersonnel.addLigue("F");
	 }, "le nom d'une ligue doit avoir au moins 2 caractères");
		
	}
	@Test //creation ligue et set admin (on en a besoin pour test suivant)
	void employes() throws SauvegardeImpossible, DateInvalide
	{
		Ligue Viki = gestionPersonnel.addLigue("VIKING");
		Employe Lagartha = Viki.addEmploye("Lagartha", "Queen", "Ragnar@viking.dkk","bjorn", null, null);
		Viki.setAdministrateur(Lagartha);
		assertEquals(Lagartha, Viki.getAdministrateur());
		
		Ligue spn = gestionPersonnel.addLigue("Supernatural 1 to :15");
		//test sur le setteur:
		spn.setNom("Dean");
		assertEquals("Dean", spn.getNom());

	}
	void admin() throws SauvegardeImpossible, DateInvalide
	{
		//recréer la ligue spn 
		Ligue spn = gestionPersonnel.addLigue("Supernatural 1 to :15");
		assertEquals(gestionPersonnel.getRoot(), spn.getAdministrateur());
		Employe Lagartha = spn.addEmploye("Lagartha", "Queen", "Ragnar@viking.dkk","bjorn", null, null);

		Employe zizou = spn.addEmploye("Zinedine", "Zidane", "zz@zz.zz", "zz", null, null);
		spn.setAdministrateur(zizou);
		assertEquals(zizou, spn.getAdministrateur());
		
		//test sur le setAdmin faux
		assertThrows(DroitsInsuffisants.class, () -> {
              spn.setAdministrateur(Lagartha);	
         }, "Une ligue ne peut être administrée par un employé d'une ligue différente");
		
		Employe Bobby = spn.addEmploye("Bobby", "Singer", "balls@guns.sf", "jj", null, null);
		
		//tets sur getEmployes, que les employés s'y ajoutent bien
		
		assertEquals(2, spn.getEmployes().size());
		assertTrue(spn.getEmployes().contains(Bobby));
		assertTrue(spn.getEmployes().contains(zizou));
		spn.setAdministrateur(Bobby);
		//test suppression employe
		spn.remove(Bobby);
		assertFalse(spn.getEmployes().contains(Bobby));
		assertTrue(spn.getEmployes().contains(zizou));
		
		assertEquals(Bobby,spn.getAdministrateur());
	}
		void rootEtAdmin() throws SauvegardeImpossible, DateInvalide
		{
		Ligue Viki = gestionPersonnel.addLigue("VIKING");
		Employe Lagartha = Viki.addEmploye("Lagartha", "Queen", "Ragnar@viking.dkk","bjorn", null, null);

		Viki.setAdministrateur(Lagartha);
		Viki.remove(Lagartha);
		assertEquals("VIKING", Viki.getNom());
		//l'employe est bien supprimé de la ligue
		assertFalse(Viki.getEmployes().contains(Lagartha));
		//l'employe reste quand meme admin de la ligue
		
		Employe root = gestionPersonnel.getRoot();
        assertNotEquals(root, Viki.getAdministrateur());
        
        Viki.setAdministrateur(Lagartha);

        assertEquals(Lagartha,Viki.getAdministrateur());
        Viki.remove(Lagartha);
        
        Viki.setAdministrateur(gestionPersonnel.getRoot());
		assertEquals(gestionPersonnel.getRoot(), Viki.getAdministrateur());
		
		
	}

	@Test //creation d'un employe
	void addEmploye() throws SauvegardeImpossible, DateInvalide
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty", LocalDate.parse("2024-01-13"), LocalDate.parse("2024-01-14")); 
		assertEquals(employe, ligue.getEmployes().first());
	}
	
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
	
}
