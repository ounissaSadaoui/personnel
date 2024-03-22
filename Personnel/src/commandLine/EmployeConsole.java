package commandLine;

import static commandLineMenus.rendering.examples.util.InOut.getString;

import commandLineMenus.ListOption;
import commandLineMenus.Menu;
import commandLineMenus.Option;
import personnel.DateInvalide;
import personnel.Employe;
import personnel.SauvegardeImpossible;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
		    menu.add(ajouterDateArrivee(employe));
			menu.add(ajouterDateDepart(employe));
			menu.addBack("q");
			return menu;
	}

	private Option changerNom(final Employe employe)
	{
		return new Option("Changer le nom", "n", 
				() -> {try {
					employe.setNom(getString("Nouveau nom : "));
				} catch (SauvegardeImpossible | DateInvalide e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}}
			);
	}
	
	private Option changerPrenom(final Employe employe)
	{
		return new Option("Changer le prénom", "p", () -> {try {
			employe.setPrenom(getString("Nouveau prénom : "));
		} catch (SauvegardeImpossible | DateInvalide e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}});
	}
	
	private Option changerMail(final Employe employe)
	{
		return new Option("Changer le mail", "e", () -> {try {
			employe.setMail(getString("Nouveau mail : "));
		} catch (SauvegardeImpossible | DateInvalide e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}});
	}
	
	private Option changerPassword(final Employe employe)
	{
		return new Option("Changer le password", "x", () -> {try {
			employe.setPassword(getString("Nouveau password : "));
		} catch (SauvegardeImpossible | DateInvalide e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}});
	}
	
	 private Option ajouterDateDepart(final Employe employe) {
	     return new Option("Ajouter une date de départ", "d", () -> {
	         boolean saisieValide = false;

	         do {
	             try {
	                 String dateString = getString("Date de départ :");
	                 LocalDate dateDepart = LocalDate.parse(dateString);

	                 employe.setDateDepart(dateDepart);
	                 saisieValide = true; 
	             } catch (DateTimeParseException e) {
	                 System.out.println("La date doit être au format 'AAAA-MM-JJ'. Veuillez réessayer.");
	             } catch (DateInvalide e) {
	                 System.out.println("Date invalide. Veuillez réessayer.");
	             } catch (SauvegardeImpossible e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	         } while (!saisieValide);
	     });
	 }
	 private Option ajouterDateArrivee(final Employe employe) {
	     return new Option("Ajouter une date d'arrivée", "a", () -> {
	         boolean saisieValide = false;

	         do {
	             try {
	                 String dateString = getString("Date d'arrivée :");
	                 LocalDate dateDepart = LocalDate.parse(dateString);

	                 employe.setDateArrivee(dateDepart);
	                 saisieValide = true; 
	             } catch (DateTimeParseException e) {
	                 System.out.println("La date doit être au format 'AAAA-MM-JJ'. Veuillez réessayer.");
	             } catch (DateInvalide e) {
	                 System.out.println("Date invalide. Veuillez réessayer.");
	             } catch (SauvegardeImpossible e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	         } while (!saisieValide);
	     });
	 }

}
