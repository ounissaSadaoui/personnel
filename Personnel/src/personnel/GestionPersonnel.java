package personnel;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	//cchngé à public aussi tension :
	public Employe root  ; 
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
	
	public static GestionPersonnel getGestionPersonnel() throws SauvegardeImpossible, DateInvalide
	{
		if (gestionPersonnel == null)
		{
			gestionPersonnel = passerelle.getGestionPersonnel();
			if (gestionPersonnel == null)
				gestionPersonnel = new GestionPersonnel();
		}
		return gestionPersonnel;
	}

	public GestionPersonnel() throws SauvegardeImpossible
	{
		if (gestionPersonnel != null) {
			throw new RuntimeException("Vous ne pouvez créer qu'une seule instance de cet objet.");
		}
		/*try {
			root = new Employe(this, null, "root", "", "", "toor", null, null);
		} catch (DateInvalide e) {
			// On ne fait rien car on sait qu'il ne peut rien se passer ici, la creation de root est forcément valide
		}*/
		ligues = new TreeSet<>();
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
	/**
	 * Transmet l'ordre d'ajout d'un employé à la passerelle
	 */
	int insert(Employe employe) throws SauvegardeImpossible
	{
		return passerelle.insert(employe);
	}

	/**
	 * Retourne le root (super-utilisateur).
	 * @return le root.
	 */
	
	public Employe getRoot()
	{
		return root;
	}
	
	public void addRoot() throws SauvegardeImpossible {
        try {
            // Utiliser la connexion définie dans JDBC
            Connection connection = new jdbc.JDBC().connection;
            
            // Requête SQL pour sélectionner les informations du root depuis la base de données
            String requete = "SELECT * FROM employe WHERE nom = 'root'";
            PreparedStatement instruction = connection.prepareStatement(requete);
            ResultSet resultSet = instruction.executeQuery();

            if (resultSet.next()) {
                String nom = resultSet.getString("nom");
                String password = resultSet.getString("password");
                
                // Créer une nouvelle instance d'employé pour le root
                Employe root = new Employe(this, -1, null, nom, "", password, "", null, null);
                
                // Affecter le root à la variable d'instance root de la classe GestionPersonnel
                this.root = root;
            } else {
            	return ;
            }
            resultSet.close();
            instruction.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SauvegardeImpossible(e);
        }
    }
}
