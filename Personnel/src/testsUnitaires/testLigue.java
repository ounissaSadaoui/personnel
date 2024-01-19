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
		
		//creation ligue et set admin (on en a besoin pour test suivant)
		Ligue Viki = gestionPersonnel.addLigue("VIKING");
		Employe Lagartha = Viki.addEmploye("Lagartha", "Queen", "Ragnar@viking.dkk","bjorn", null, null);
		Viki.setAdministrateur(Lagartha);
		assertEquals(Lagartha, Viki.getAdministrateur());
		
		//test sur le setteur:
		spn.setNom("Dean");
		assertEquals("Dean", spn.getNom());

		//test sur getAdministrateur 
		// ce test devrait prouver que si aucun admin n'est set, c'est le root qui est admin,

	/*	spn.getAdministrateur();
		assertEquals("root   (super-utilisateur)", spn.getAdministrateur());*/
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
		
		Viki.setAdministrateur(Lagartha);
		Viki.remove(Lagartha);
		assertEquals("VIKING", Viki.getNom());
		//l'employe est bien supprimé de la ligue
		assertFalse(Viki.getEmployes().contains(Lagartha));
		//l'employe reste quand meme admin de la ligue
        assertEquals(Lagartha, Viki.getAdministrateur());
		
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
