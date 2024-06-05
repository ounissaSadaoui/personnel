package personnel;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

import jdbc.Credentials;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Gestion du personnel. Un seul objet de cette classe existe.
 * Il n'est pas possible d'instancier directement cette classe, 
 * la méthode {@link #getGestionPersonnel getGestionPersonnel} 
 * le fait automatiquement et retourne toujours le même objet.
 * Dans le cas où {@link #sauvegarder()} a été appelé lors 
 * d'une exécution précédente, c'est l'objet sauvegardé qui est
 * retourné.
 */

public class GestionPersonnel implements Serializable
{
	private static final long serialVersionUID = -105283113987886425L;
	private static GestionPersonnel gestionPersonnel = null;
	private SortedSet<Ligue> ligues;
	private SortedSet<Employe> employes;
	 Employe root  ; 
	public final static int SERIALIZATION = 1, JDBC = 2,
			TYPE_PASSERELLE = JDBC;   
	
	private static Passerelle passerelle = TYPE_PASSERELLE == JDBC ? new jdbc.JDBC() : new serialisation.Serialization();	


	/**
	 * Retourne l'unique instance de cette classe.
	 * Crée cet objet s'il n'existe déjà.
	 * @return l'unique objet de type {@link GestionPersonnel}.
	 * @throws SauvegardeImpossible 
	 * @throws DateInvalide 
	 */
	
	public static GestionPersonnel getGestionPersonnel() throws DateInvalide {
		if (gestionPersonnel == null) {
			try {
				gestionPersonnel = passerelle.getGestionPersonnel();
			} catch (SauvegardeImpossible e) {
				e.printStackTrace();
			}
			if (gestionPersonnel == null)
				gestionPersonnel = new GestionPersonnel();
		}
		return gestionPersonnel;
	}

	public GestionPersonnel() {
		if (gestionPersonnel != null)
			throw new RuntimeException("Vous ne pouvez créer qu'une seule instance de cet objet.");
		ligues = new TreeSet<>();
		employes = new TreeSet<>();
		gestionPersonnel = this;
	}
	
	public void sauvegarder() throws SauvegardeImpossible
	{
		passerelle.sauvegarderGestionPersonnel(this);
	}
	
	/**
	 * Retourne la ligue dont administrateur est l'administrateur,
	 * null s'il n'est pas un administrateur.
	 * @param administrateur l'administrateur de la ligue recherchée.
	 * @return la ligue dont administrateur est l'administrateur.
	 */
	
	public Ligue getLigue(Employe administrateur)
	{
		if (administrateur.estAdmin(administrateur.getLigue())) {
			
			return administrateur.getLigue();
			
		} else {
			
			return null;
		}
	}

	/**
	 * Retourne toutes les ligues enregistrées.
	 * @return toutes les ligues enregistrées.
	 */
	
	public SortedSet<Ligue> getLigues()
	{
		return Collections.unmodifiableSortedSet(ligues);
	}
	
	//ajout de ça pour match la sortedlIST LIGUE
	
	public SortedSet<Employe> getEmployes()
	{
		return Collections.unmodifiableSortedSet(employes);
	}

	public Ligue addLigue(String nom) throws SauvegardeImpossible
	{
		Ligue ligue = new Ligue(this, nom); 
		ligues.add(ligue);
		return ligue;
	}
	
	public Ligue addLigue(int id, String nom) throws SauvegardeImpossible
	{
		Ligue ligue = new Ligue(this, id, nom);
		ligues.add(ligue);
		return ligue;
	}
	

	void remove(Ligue ligue)
	{
		ligues.remove(ligue);
	}
	
	int insert(Ligue ligue) throws SauvegardeImpossible
	{
		return passerelle.insert(ligue);
	}
	
	void update(Ligue ligue) throws SauvegardeImpossible
	{
		passerelle.update(ligue);

	}
	/**
	 * Transmet l'ordre d'ajout d'un employé à la passerelle
	 */
	int insert(Employe employe) throws SauvegardeImpossible
	{
		return passerelle.insert(employe);
	}

	
	void update(Employe employe) throws SauvegardeImpossible, DateInvalide
	{
		passerelle.update(employe);
	}
	/**
	 * Retourne le root (super-utilisateur).
	 * @return le root.
	 */
	
	public Employe getRoot()
	{
		return root;
	} 
	
	//addRoot sue le modèle de addLigue Quand le root est connu
	public void addRoot() throws SauvegardeImpossible, DateInvalide {
       
	    root = new Employe(this, null, "root", "", "", "toor", null, null); 

            
    }
	//ajout de addRoot ici pouir qu'on n'ait pas à appeler new employe depuis le jdbc, quand le root est inconnu
	
	public void addRoot(int id, String nom, String password, String mail, LocalDate dateArrivee, LocalDate dateDepart) throws SauvegardeImpossible, DateInvalide {
	    root = new Employe(this, id, null, nom, "", password, mail, dateArrivee, dateDepart);
	}
	void delete(Employe employe) throws SauvegardeImpossible {
		passerelle.delete(employe);

	}
	void delete(Ligue ligue) throws SauvegardeImpossible {

		passerelle.delete(ligue);

	}
}
