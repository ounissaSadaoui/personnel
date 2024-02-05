package commandLine;

import static commandLineMenus.rendering.examples.util.InOut.getString;

import commandLineMenus.ListOption;
import commandLineMenus.Menu;
import commandLineMenus.Option;
import personnel.DateInvalide;
import personnel.Employe;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class EmployeConsole 
{
	private Option afficher(final Employe employe)
	{
		return new Option("Afficher l'employé", "l", () -> {System.out.println(employe);});
	}

	ListOption<Employe> editerEmploye()
	{
		return (employe) -> editerEmploye(employe);		
	}

	Option editerEmploye(Employe employe)
	{
			Menu menu = new Menu("Gérer le compte " + employe.getNom(), "c");
			menu.add(afficher(employe));
			menu.add(changerNom(employe));
			menu.add(changerPrenom(employe));
			menu.add(changerMail(employe));
			menu.add(changerPassword(employe));
			//menu.add(ajouterDateArrivee(employe));
			//menu.add(ajouterDateDepart(employe));
			menu.addBack("q");
			return menu;
	}

	private Option changerNom(final Employe employe)
	{
		return new Option("Changer le nom", "n", 
				() -> {employe.setNom(getString("Nouveau nom : "));}
			);
	}
	
	private Option changerPrenom(final Employe employe)
	{
		return new Option("Changer le prénom", "p", () -> {employe.setPrenom(getString("Nouveau prénom : "));});
	}
	
	private Option changerMail(final Employe employe)
	{
		return new Option("Changer le mail", "e", () -> {employe.setMail(getString("Nouveau mail : "));});
	}
	
	private Option changerPassword(final Employe employe)
	{
		return new Option("Changer le password", "x", () -> {employe.setPassword(getString("Nouveau password : "));});
	}
	/*
	 * private Option ajouterDateDepart(final Employe employe)
	{
		return new Option ("Ajouter une date de départ", "d", () -> {String dateString = getString("Date de départ :");
	    LocalDate dateDepart = LocalDate.parse(dateString); 
	    try {
			employe.setDateDepart(dateDepart);
		} catch (DateTimeParseException e) {
			System.out.print("la date doit être au format 'AAAA-MM-JJ'");
			e.printStackTrace();
		} catch (DateInvalide e) {
			System.out.print("Date Invalide");
			e.printStackTrace();
		}});
	}
	private Option ajouterDateArrivee(final Employe employe)
	{
		return new Option ("Ajouter une date d'arrivée", "a", () -> {String dateString = getString("Date d'arrivée :");
	    LocalDate dateArrivee = LocalDate.parse(dateString); 
	    try {
			employe.setDateArrivee(dateArrivee);
		} catch (DateInvalide e) {
			System.out.print("la date doit être au format 'AAAA-MM-JJ'");
			e.printStackTrace();
		}});
	}
	 */


}
