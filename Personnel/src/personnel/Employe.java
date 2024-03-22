package personnel;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.*;
import java.util.SortedSet;
import java.util.TreeSet;

import personnel.DateInvalide;

/**
 * Employé d'une ligue hébergée par la M2L. Certains peuvent 
 * être administrateurs des employés de leur ligue.
 * Un seul employé, rattaché à aucune ligue, est le root.
 * Il est impossible d'instancier directement un employé, 
 * il faut passer la méthode {@link Ligue#addEmploye addEmploye}.
 */

public class Employe implements Serializable, Comparable<Employe> 

{
	private static final long serialVersionUID = 4795721718037994734L;
	private String nom, prenom, password, mail;
	private LocalDate dateArrivee , dateDepart;
	private Ligue ligue;
	private GestionPersonnel gestionPersonnel;
	//ajout de la var d'instance id
	private int id;
	//ajout des var pour surcharge su constructeur
	private SortedSet<Employe> employes;
	private Employe administrateur;
	
	//attention you changed it to public!!!
	Employe(GestionPersonnel gestionPersonnel, 
			Ligue ligue, 
			String nom, 
			String prenom, 
			String mail, 
			String password, 
			LocalDate dateArrivee, 
			LocalDate dateDepart) throws DateInvalide, SauvegardeImpossible
{
		this.gestionPersonnel = gestionPersonnel;
		this.nom = nom;
		this.prenom = prenom;
		this.password = password;
		this.mail = mail;
		this.ligue = ligue;
		// on passe par les setter pour pouvoir valider les dates
		setDateArrivee(dateArrivee);
		setDateDepart(dateDepart);
		this.id = gestionPersonnel.insert(this); 

		
    }
	public Employe(GestionPersonnel gestionPersonnel, int id, Ligue ligue,String nom, String prenom, String password, String mail,  LocalDate dateArrivee, LocalDate dateDepart) throws SauvegardeImpossible, DateInvalide
	{
		this.nom = nom;
		this.prenom = prenom;
		this.ligue = ligue;
		this.mail= mail;
		this.password= password;
		this.dateArrivee= dateArrivee;
		this.dateDepart = dateDepart;
		this.gestionPersonnel = gestionPersonnel;
		this.id = id;
		gestionPersonnel.updateEmploye(this);
	}
	/**
	 * Retourne vrai ssi l'employé est administrateur de la ligue 
	 * passée en paramètre.
	 * @return vrai ssi l'employé est administrateur de la ligue 
	 * passée en paramètre.
	 * @param ligue la ligue pour laquelle on souhaite vérifier si this 
	 * est l'admininstrateur.
	 */
	
	
	
	public boolean estAdmin(Ligue ligue)
	{
		return ligue.getAdministrateur() == this;
	}
	
	/**
	 * Retourne vrai ssi l'employé est le root.
	 * @return vrai ssi l'employé est le root.
	 */
	
	public boolean estRoot()
	{
		return gestionPersonnel.getRoot() == this;
	}
	
	/**
	 * Retourne la date d'arrivée de l'employé.
	 * @return la date d'arrivée de l'employé. 
	 */
	
	public LocalDate getDateArrivee()
	{
		return dateArrivee;
	}
	
	/**
	 * Retourne la date d'arrivée de l'employé.
	 * @param la date d'arrivée de l'employé. 
	 * @throws SauvegardeImpossible 
	 */
	
	public void setDateArrivee(LocalDate dateArrivee) throws DateInvalide, SauvegardeImpossible
	{   
		//cas date depart avant date arrivee
        if (dateDepart != null && dateArrivee != null && dateArrivee.isAfter(dateDepart)) {
            throw new DateInvalide("La date d'arrivée ne peut être postérieure à celle de départ");
        } else {
            //dans les autres cas
            this.dateArrivee = dateArrivee;
    		gestionPersonnel.updateEmploye(this);

        }
	}
	
	/**
	 * Retourne la date de départ de l'employé.
	 * @return la date de départ de l'employé. 
	 */
	
	public LocalDate getDateDepart() 
	{
		return dateDepart;
	}
	
	/**
	 * change la date de départ de l'employé.
	 * @param la date de départ de l'employé. 
	 * @throws SauvegardeImpossible 
	 */
	
	public void setDateDepart(LocalDate dateDepart) throws DateInvalide, DateTimeParseException, SauvegardeImpossible
	{
		if ((dateArrivee != null && dateDepart != null && dateDepart.isBefore(dateArrivee)) || (dateArrivee == null && dateDepart != null )) { 
			throw new DateInvalide("La date de départ ne peut être antérieure à celle d'arrivée");
	    } else {
	        //dans les autres cass
	        this.dateDepart = dateDepart;
			gestionPersonnel.updateEmploye(this);

	    }
	}
	
	/**
	 * Retourne le nom de l'employé.
	 * @return le nom de l'employé. 
	 */
	
	public String getNom()
	{
		return nom;
	}

	/**
	 * Change le nom de l'employé.
	 * @param nom le nouveau nom.
	 * @throws DateInvalide 
	 * @throws SauvegardeImpossible 
	 */
	
	public void setNom(String nom) throws SauvegardeImpossible, DateInvalide
	{
		this.nom = nom;
		gestionPersonnel.updateEmploye(this);

	}

	/**
	 * Retourne le prénom de l'employé.
	 * @return le prénom de l'employé.
	 */
	
	public String getPrenom()
	{
		return prenom;
	}
	
	/**
	 * Change le prénom de l'employé.
	 * @param prenom le nouveau prénom de l'employé. 
	 * @throws DateInvalide 
	 * @throws SauvegardeImpossible 
	 */

	public void setPrenom(String prenom) throws SauvegardeImpossible, DateInvalide
	{
		this.prenom = prenom;
		gestionPersonnel.updateEmploye(this);

	}

	/**
	 * Retourne le mail de l'employé.
	 * @return le mail de l'employé.
	 */
	
	public String getMail()
	{
		return mail;
	}
	
	/**
	 * Change le mail de l'employé.
	 * @param mail le nouveau mail de l'employé.
	 * @throws DateInvalide 
	 * @throws SauvegardeImpossible 
	 */

	public void setMail(String mail) throws SauvegardeImpossible, DateInvalide
	{
		this.mail = mail;
		gestionPersonnel.updateEmploye(this);

	}

	/**
	 * Retourne vrai ssi le password passé en paramètre est bien celui
	 * de l'employé.
	 * @return vrai ssi le password passé en paramètre est bien celui
	 * de l'employé.
	 * @param password le password auquel comparer celui de l'employé.
	 */
	
	public boolean checkPassword(String password)
	{
		return this.password.equals(password);
	}

	/**
	 * Change le password de l'employé.
	 * @param password le nouveau password de l'employé. 
	 * @throws DateInvalide 
	 * @throws SauvegardeImpossible 
	 */
	
	public void setPassword(String password) throws SauvegardeImpossible, DateInvalide
	{
		this.password= password;
		gestionPersonnel.updateEmploye(this);
	}

	public String getPassword()
	{
		return password;
	}
	
	//get set pour id 
	
	public void setId (int id)
	{
		this.id = id;
	}
	
	public int getId ()
	{
		return id;
	}
	/**
	 * Retourne la ligue à laquelle l'employé est affecté.
	 * @return la ligue à laquelle l'employé est affecté.
	 */
	
	public Ligue getLigue()
	{
		return ligue;
	}

	/**
	 * Supprime l'employé. Si celui-ci est un administrateur, le root
	 * récupère les droits d'administration sur sa ligue.
	 * @throws SauvegardeImpossible 
	 */
	

	public void remove() throws SauvegardeImpossible
	{
		Employe root = gestionPersonnel.getRoot();
		if (this != root)
		{
			if (estAdmin(getLigue()))
				getLigue().setAdministrateur(root);
			getLigue().remove(this);
		}
		else
			throw new ImpossibleDeSupprimerRoot();
	}

	@Override
	public int compareTo(Employe autre)
	{
		int cmp = getNom().compareTo(autre.getNom());
		if (cmp != 0)
			return cmp;
		return getPrenom().compareTo(autre.getPrenom());
	}
	
	@Override
	public String toString()
	{
		String res = nom + " " + prenom + " " + mail + " "+ dateArrivee + " " + dateDepart + " (";
		if (estRoot())
			res += "super-utilisateur";
		else
			res += ligue.toString();
		return res + ")";
	}
}
