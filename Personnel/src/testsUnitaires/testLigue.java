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
		//cas normal
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		assertEquals("Fléchettes", ligue.getNom());
	};
	@Test
	void createLigueCharSpe() throws SauvegardeImpossible, DateInvalide{
			// test sur les caractères spéciaux dans le nom
	     Ligue spn = gestionPersonnel.addLigue("Supernatural 1 to :15");
		 assertEquals("Supernatural 1 to :15", spn.getNom());
			
		};
		@Test
	void createLiguePassePas() throws SauvegardeImpossible, DateInvalide{
		//re test si le nom de la ligue est composé d'une seule lettre
		assertThrows(SauvegardeImpossible.class, () -> {
			Ligue lig = gestionPersonnel.addLigue("F");
	 }, "le nom d'une ligue doit avoir au moins 2 caractères");
		
	};
	@Test //creation ligue et set admin (on en a besoin pour test suivant)
	void employes() throws SauvegardeImpossible, DateInvalide
	{
		Ligue Viki = gestionPersonnel.addLigue("VIKING");
		Employe Lagartha = Viki.addEmploye("Lagartha", "Queen", "Ragnar@viking.dkk","bjorn", null, null);

		//l'employe est bien ajouté à la ligue
		assertTrue(Viki.getEmployes().contains(Lagartha));
	};
	@Test
	void rootIsAdmin() throws SauvegardeImpossible, DateInvalide
	{
		//recréer la ligue spn 
		Ligue spn = gestionPersonnel.addLigue("Supernatural 1 to :15");
		//aucun admin set, le root est admin
		assertEquals(gestionPersonnel.getRoot(), spn.getAdministrateur());
	}
	@Test
	void setAdmin() throws SauvegardeImpossible, DateInvalide
	{
		Ligue spn = gestionPersonnel.addLigue("Supernatural 1 to :15");
		Employe Lagartha = spn.addEmploye("Lagartha", "Queen", "Ragnar@viking.dkk","bjorn", null, null);

		Employe zizou = spn.addEmploye("Zinedine", "Zidane", "zz@zz.zz", "zz", null, null);
		spn.setAdministrateur(zizou);
		
		//on set un admin, on le voit bien
		assertEquals(zizou, spn.getAdministrateur());
	}
	@Test
	void setAdminFaux() throws SauvegardeImpossible, DateInvalide
	{
		Ligue Viki = gestionPersonnel.addLigue("VIKING");
		Ligue spn = gestionPersonnel.addLigue("Supernatural 1 to :15");
		Employe Lagartha = Viki.addEmploye("Lagartha", "Queen", "Ragnar@viking.dkk","bjorn", null, null);
		Employe zizou = spn.addEmploye("Zinedine", "Zidane", "zz@zz.zz", "zz", null, null);
		//test sur le setAdmin faux
		assertThrows(DroitsInsuffisants.class, () -> {
              spn.setAdministrateur(Lagartha);	
         }, "Une ligue ne peut être administrée par un employé d'une ligue différente");
	}
	@Test
	void getEmployes() throws SauvegardeImpossible, DateInvalide
	{
		//tets sur getEmployes, que les employés s'y ajoutent bien
		Ligue spn = gestionPersonnel.addLigue("Supernatural 1 to :15");
		Employe zizou = spn.addEmploye("Zinedine", "Zidane", "zz@zz.zz", "zz", null, null);
		Employe Lagartha = spn.addEmploye("Lagartha", "Queen", "Ragnar@viking.dkk","bjorn", null, null);
		assertEquals(2, spn.getEmployes().size());
		assertTrue(spn.getEmployes().contains(zizou));
	};
	@Test
	void containsEmploye() throws SauvegardeImpossible, DateInvalide
	{
		Ligue spn = gestionPersonnel.addLigue("Supernatural 1 to :15");
		Employe Bobby = spn.addEmploye("Bobby", "Singer", "balls@guns.sf", "jj", null, null);
		assertTrue(spn.getEmployes().contains(Bobby));
	};
	    //test suppression employe
	@Test
	void removeEmploye() throws SauvegardeImpossible, DateInvalide
		{
			Ligue spn = gestionPersonnel.addLigue("Supernatural 1 to :15");
			Employe Bobby = spn.addEmploye("Bobby", "Singer", "balls@guns.sf", "jj", null, null);
	        Bobby.remove();		//bobby n'est plus dans la ligue
			assertFalse(spn.getEmployes().contains(Bobby));
			//il n'est plus admin de la ligue
		//	assertNotEquals(Bobby, spn.getAdministrateur());
			//assertEquals(Bobby, spn.getAdministrateur());

		};
    @Test
    void removeAdmin() throws SauvegardeImpossible, DateInvalide
    {
	       Ligue spn = gestionPersonnel.addLigue("Supernatural 1 to :15");
	       Employe Bobby = spn.addEmploye("Bobby", "Singer", "balls@guns.sf", "jj", null, null);
	       spn.setAdministrateur(Bobby);
	       Bobby.remove();
	       assertNotEquals(Bobby, spn.getAdministrateur());
    }
		@Test
	void rootEtAdmin() throws SauvegardeImpossible, DateInvalide
		{
		Ligue Viki = gestionPersonnel.addLigue("VIKING");
		Employe Lagartha = Viki.addEmploye("Lagartha", "Queen", "Ragnar@viking.dkk","bjorn", null, null);
       
		Viki.remove();
        assertFalse(gestionPersonnel.getLigues().contains(Viki));
		//l'employe est bien supprimé de la ligue
		assertTrue(Viki.getEmployes().contains(Lagartha));
		//l'employe reste quand meme admin de la ligue
		
		Employe root = gestionPersonnel.getRoot();
        assertEquals(true, root.estRoot());
        
        Viki.setAdministrateur(Lagartha);

        assertEquals(Lagartha,Viki.getAdministrateur());
        Lagartha.remove();        
		assertEquals(true, Viki.getAdministrateur().estRoot());
		
		
	};

	@Test //creation d'un employe
	void addEmploye() throws SauvegardeImpossible, DateInvalide
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty", LocalDate.parse("2024-01-13"), LocalDate.parse("2024-01-14")); 
		assertEquals(employe, ligue.getEmployes().first());
	};
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
		//tets 
	};
	
}
