package jdbc;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import personnel.*;

public class JDBC implements Passerelle 
{
	public Connection connection;

	public JDBC()
	{
		try
		{
			Class.forName(Credentials.getDriverClassName());
			connection = DriverManager.getConnection(Credentials.getUrl(), Credentials.getUser(), Credentials.getPassword());
		}
		catch (ClassNotFoundException e)
		{
			System.out.println("Pilote JDBC non installé.");
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
	}
	
	@Override
	/*
	public GestionPersonnel getGestionPersonnel() throws SauvegardeImpossible , DateInvalide{
	    GestionPersonnel gestionPersonnel = new GestionPersonnel();
	    try 
		{
			String requete = "select * from ligue";
			Statement instruction = connection.createStatement();
			ResultSet ligues = instruction.executeQuery(requete);
			while (ligues.next())
				try {
					gestionPersonnel.addLigue(ligues.getInt(1), ligues.getString(2));
				} catch (SauvegardeImpossible e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
	    try {
	        // Requête SQL pour sélectionner les informations du root depuis la base de données
	        String requete = "SELECT * FROM employe WHERE idLigue IS NULL";

	        PreparedStatement instruction = connection.prepareStatement(requete);
	        ResultSet resultSet = instruction.executeQuery();

	        // Vérifier si un utilisateur root existe déjà dans la base de données
	        if (resultSet.next()) {
	            // Si un utilisateur root existe, récupérer ses informations
	            int id = resultSet.getInt("idEmploye");
	            String nom = resultSet.getString("nom");
	            String password = resultSet.getString("password");
	            String mail = resultSet.getString("mail");
	            LocalDate dateArrivee = resultSet.getDate("dateArrivee") != null ? resultSet.getDate("dateArrivee").toLocalDate() : null;
	            LocalDate dateDepart = resultSet.getDate("dateDepart") != null ? resultSet.getDate("dateDepart").toLocalDate() : null;

	            // Créer un nouvel objet Employe pour le root
	            gestionPersonnel.addRoot(id, nom, password, mail, dateArrivee, dateDepart);
	            // Affecter le root à la variable d'instance root de la classe GestionPersonnel
	        }

	        resultSet.close();
	        instruction.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new SauvegardeImpossible(e);
	    }
	    return gestionPersonnel;
	}*/
	public GestionPersonnel getGestionPersonnel() throws SauvegardeImpossible, DateInvalide {
	    GestionPersonnel gestionPersonnel = new GestionPersonnel();
	    try {
	        // Charger les informations sur le root depuis la base de données
	        String requeteRoot = "SELECT * FROM employe WHERE idLigue IS NULL";
	        PreparedStatement instructionRoot = connection.prepareStatement(requeteRoot);
	        ResultSet resultSetRoot = instructionRoot.executeQuery();

	        // Vérifier si un utilisateur root existe déjà dans la base de données
	        if (resultSetRoot.next()) {
	            // Si un utilisateur root existe, récupérer ses informations
	            int idRoot = resultSetRoot.getInt("idEmploye");
	            String nomRoot = resultSetRoot.getString("nom");
	            String passwordRoot = resultSetRoot.getString("password");
	            String mailRoot = resultSetRoot.getString("mail");
	            LocalDate dateArriveeRoot = resultSetRoot.getDate("dateArrivee") != null ? resultSetRoot.getDate("dateArrivee").toLocalDate() : null;
	            LocalDate dateDepartRoot = resultSetRoot.getDate("dateDepart") != null ? resultSetRoot.getDate("dateDepart").toLocalDate() : null;

	            // Utiliser la méthode addRoot pour créer le root
	            gestionPersonnel.addRoot(idRoot, nomRoot, passwordRoot, mailRoot, dateArriveeRoot, dateDepartRoot);
	        }
	        

	        resultSetRoot.close();
	        instructionRoot.close();

	        // Charger les informations sur les ligues depuis la base de données
	        String requeteLigues = "SELECT * FROM ligue";
	        Statement instructionLigues = connection.createStatement();
	        ResultSet resultSetLigues = instructionLigues.executeQuery(requeteLigues);

	        // Parcourir les résultats et ajouter les ligues à l'objet GestionPersonnel
	        while (resultSetLigues.next()) {
	            int idLigue = resultSetLigues.getInt("idLigue");
	            String nomLigue = resultSetLigues.getString("nom");
	            gestionPersonnel.addLigue(idLigue, nomLigue);
	        }

	        resultSetLigues.close();
	        instructionLigues.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new SauvegardeImpossible(e);
	    }
	    return gestionPersonnel;
	}

	
	@Override
	public void sauvegarderGestionPersonnel(GestionPersonnel gestionPersonnel) throws SauvegardeImpossible 
	{
		close();
	}
	
	public void close() throws SauvegardeImpossible
	{
		try
		{
			if (connection != null)
				connection.close();
		}
		catch (SQLException e)
		{
			throw new SauvegardeImpossible(e);
		}
	}
	//insertion des ligues 
	@Override
	public int insert(Ligue ligue) throws SauvegardeImpossible 
	{
		try 
		{
			PreparedStatement instruction;
			instruction = connection.prepareStatement("insert into ligue (nom) values(?)", Statement.RETURN_GENERATED_KEYS);
			instruction.setString(1, ligue.getNom());		
			instruction.executeUpdate();
			ResultSet id = instruction.getGeneratedKeys();
			id.next();
			return id.getInt(1);
		} 
		catch (SQLException exception) 
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}		
	}

	@Override
	public int insert(Employe employe) throws SauvegardeImpossible {
	    try {
	        //On véridie d'abird si l'employé existe déjà
	        PreparedStatement checkIfExists = connection.prepareStatement("SELECT COUNT(*) FROM employe WHERE nom = ?");
	        checkIfExists.setString(1, employe.getNom());
	        ResultSet result = checkIfExists.executeQuery();
	        result.next();
	        int rowCount = result.getInt(1);
	        if (rowCount > 0) {
	        }
	        
	        // Si l'employé n'existe pas, on insère les données
	        PreparedStatement instruction;
            instruction = connection.prepareStatement("INSERT INTO employe (nom, password) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
	        instruction.setString(1, employe.getNom());
	        instruction.setString(2, employe.getPassword());
	        instruction.executeUpdate();
	        ResultSet id = instruction.getGeneratedKeys();
	        id.next();
	        return id.getInt(1);
	    } catch (SQLException exception) {
	        exception.printStackTrace();
	        throw new SauvegardeImpossible(exception);
	    }
	}

	@Override
	public Employe getRoot(Employe root) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
