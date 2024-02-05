package commandLine;

import static commandLineMenus.rendering.examples.util.InOut.getString;

import java.util.ArrayList;

import commandLineMenus.List;
import commandLineMenus.Menu;
import commandLineMenus.Option;

import personnel.*;

public class LigueConsole 
{
	private GestionPersonnel gestionPersonnel;
	private EmployeConsole employeConsole;

	public LigueConsole(GestionPersonnel gestionPersonnel, EmployeConsole employeConsole)
	{
		this.gestionPersonnel = gestionPersonnel;
		this.employeConsole = employeConsole;
	}

	Menu menuLigues()
	{
		Menu menu = new Menu("Gérer les ligues", "l");
		menu.add(afficherLigues());
		menu.add(ajouterLigue());
		menu.add(selectionnerLigue());
		menu.addBack("q");
		return menu;
	}

	private Option afficherLigues()
	{
		return new Option("Afficher les ligues", "l", () -> {System.out.println(gestionPersonnel.getLigues());});
	}

	private Option afficher(final Ligue ligue)
	{
		return new Option("Afficher la ligue", "l", 
				() -> 
				{
					System.out.println(ligue);
					System.out.println("administrée par " + ligue.getAdministrateur());
				}
		);
	}
	private Option afficherEmployes(final Ligue ligue)
	{
		return new Option("Afficher les employes", "l", () -> {System.out.println(ligue.getEmployes());});
	}

	private Option ajouterLigue()
	{
		return new Option("Ajouter une ligue", "a", () -> 
		{
			try
			{
				gestionPersonnel.addLigue(getString("nom : "));
			}
			catch(SauvegardeImpossible exception)
			{
				System.err.println("Impossible de sauvegarder cette ligue");
			}
		});
	}
	
	private Menu editerLigue(Ligue ligue)
	{
		Menu menu = new Menu("Editer " + ligue.getNom());
		menu.add(afficher(ligue));
		menu.add(gererEmployes(ligue));
		 //menu.add(changerAdministrateur(ligue));
		menu.add(changerNom(ligue));
		menu.add(supprimer(ligue));
		menu.addBack("q");
		return menu;
	}

	private Option changerNom(final Ligue ligue)
	{
		return new Option("Renommer", "r", 
				() -> {try {
					ligue.setNom(getString("Nouveau nom : "));
				} catch (SauvegardeImpossible e) {
					e.printStackTrace();
				}});
	}

	private Option ajouterEmploye(final Ligue ligue)
	{
		return new Option("Ajouter un employé", "a",
				() -> 
				{
					// 
					try {
						ligue.addEmploye(
							getString("nom : "), 
							getString("prenom : "), 
							getString("mail : "), 
							getString("password : "), null, null);
					} catch (DateInvalide e) {
						e.printStackTrace();
					}
				}
		);
	}
	
	private Menu gererEmployes(Ligue ligue)
	{
		Menu menu = new Menu("Gérer les employés de " + ligue.getNom(), "e");
		menu.add(afficherEmployes(ligue));
		menu.add(ajouterEmploye(ligue));
		//menu.add(modifierEmploye(ligue));
		//menu.add(supprimerEmploye(ligue));
		menu.add(selectionnerEmploye(ligue));
		menu.addBack("q");
		return menu;
	}
	
/*original pour remove 
	private List<Employe> supprimerEmploye(final Ligue ligue)
	{
		
		return new List<>("Supprimer un employé", "s", 
				() -> new ArrayList<>(ligue.getEmployes()),
				(index, element) -> {element.remove();}
				);
	}*/
	private Option supprimerEmploye (final Employe employe)
	{
		return new Option ("Supprimer ", "x",
				() -> {employe.remove();});
	}
	
	private Option changerAdministrateur(final Ligue ligue, final Employe employe)
	{
			 return new Option("Mettre en Administrateur", "a", () -> 
						 {ligue.setAdministrateur(employe);
						 System.out.println(employe.getNom() + " est maintentant Administrateur de: " + ligue);
});
	
	}		
/*
	private List<Employe> modifierEmploye(final Ligue ligue)
	{
		return new List<>("Modifier un employé", "e", 
				() -> new ArrayList<>(ligue.getEmployes()),
				employeConsole.editerEmploye()
				);
	}*/
	/* originale, pour modifier, */
/*
	private Option modifierEmploye(final Employe employe)
	{
		return new List<>("Modifier un employé", "e", 
				() -> new ArrayList<>( ),
				employeConsole.editerEmploye()
				);
	}
	private Option modifierEmploye(final Employe employe)
	{
		return new Option ("Modifier l'employé ", "m", () -> 	employeConsole.editerEmploye(employe)
				);
	}*/
	
	private Option supprimer(Ligue ligue)
	{
		return new Option("Supprimer", "d", () -> {ligue.remove();});
	}
	
	//Ajout de la méthode de sélection d'un employe avant sa modification 
	/*private Menu selectionnerEmploye(Ligue ligue)
	{
		Menu menu = new Menu("Sélectionner un employé de  " + ligue.getNom(), "s");
	//	menu.add(modifierEmploye(ligue));
	//	menu.add(supprimerEmploye(ligue));
		menu.add(editerEmploye(ligue));
		menu.addBack("q");
		return menu;
	}*/
	private List<Employe> selectionnerEmploye(Ligue ligue)
	{
		return new List<Employe>("Sélectionner un Employe", "g", 
				() -> new ArrayList<>(ligue.getEmployes()),
				(element) -> editerEmploye(element, ligue)
				);
	}
	private List<Ligue> selectionnerLigue()
	{
		return new List<Ligue>("Sélectionner une ligue", "e", 
				() -> new ArrayList<>(gestionPersonnel.getLigues()),
				(element) -> editerLigue(element)
				);
	}
	
	private Menu editerEmploye(Employe employe, Ligue ligue)
	{
        Menu menu = new Menu("Editer " + employe.getNom());
		menu.add(employeConsole.editerEmploye(employe));
		menu.add(changerAdministrateur(ligue, employe));
		menu.add(supprimerEmploye(employe));
		//menu.add(supprimerEmploye(ligue));

		//menu.add(changerAdministrateur(ligue));
		menu.addBack("q");
		return menu;
	}

}
