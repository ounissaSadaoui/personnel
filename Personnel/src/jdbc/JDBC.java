package jdbc;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.Date;
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
/*
	@Override
	public GestionPersonnel getGestionPersonnel() throws SauvegardeImpossible, DateInvalide {
	    GestionPersonnel gestionPersonnel = new GestionPersonnel();
	    try {
	    	// Requête SQL pour sélectionner les informations du root depuis la base de données
	        String requete = "SELECT * FROM employe WHERE idLigue IS NULL";
	        PreparedStatement instruction = connection.prepareStatement(requete);
	        ResultSet resultSet = instruction.executeQuery();

	        if (resultSet.next()) {
	        	 String nom = resultSet.getString("nom");
	                String password = resultSet.getString("password");
	                int id = resultSet.getInt("idEmploye");
	                gestionPersonnel.addRoot(id, nom, password, "", null, null);
	        } else if (!resultSet.next()) {
		            gestionPersonnel.addRoot();
	            } else {
	                throw new SauvegardeImpossible(new RuntimeException("Sauvegarde Impossible !!"));
	            }

	        //resultSet.close();
	        //instruction.close();
	        
	        String requeteLigue = "SELECT * FROM ligue ";
			Statement instructionLigue = connection.createStatement();
			ResultSet ligues = instructionLigue.executeQuery(requeteLigue);

			while (ligues.next()) {

				gestionPersonnel.addLigue(ligues.getInt("idLigue"), ligues.getString("nom"));

				PreparedStatement req = connection.prepareStatement("SELECT * FROM employe WHERE idLigue = ?");

				req.setInt(1, ligues.getInt("idLigue"));
				ResultSet employe = req.executeQuery();
				Ligue ligue = gestionPersonnel.getLigues().last();

				while (employe.next()) {

					int id = employe.getInt("idEmploye");
					String nom = employe.getString("nom");
					String prenom = employe.getString("prenom");
					String mail = employe.getString("mail");
					String password = employe.getString("password");
					LocalDate date_arrivee = employe.getDate("dateArrivee") != null
							? LocalDate.parse(employe.getString("dateArrivee"))
							: null;
					LocalDate date_depart = employe.getDate("dateDepart") != null
							? LocalDate.parse(employe.getString("dateDepart"))
							: null;

					Employe employee = ligue.addEmploye(nom, prenom, mail, password, date_arrivee, date_depart, id);		
					}
				}
		} catch (SQLException exception) {
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
	    }
	    return gestionPersonnel;
	}
*/
	public GestionPersonnel getGestionPersonnel() throws SauvegardeImpossible, DateInvalide {

		GestionPersonnel gestionPersonnel = new GestionPersonnel();
		try {
			// Requête SQL pour sélectionner les informations du root depuis la base de données
	        String requeteRoot = "SELECT * FROM employe WHERE idLigue IS NULL";
	        PreparedStatement instruction = connection.prepareStatement(requeteRoot);
	        ResultSet resultSet = instruction.executeQuery();

	        if (resultSet.next()) {
	        	 String nom = resultSet.getString("nom");
	                String password = resultSet.getString("password");
	                int id = resultSet.getInt("idEmploye");
	                gestionPersonnel.addRoot(id, nom, password, "", null, null);
	        } else if (!resultSet.next()) {
		            gestionPersonnel.addRoot();
	            } else {
	                throw new SauvegardeImpossible(new RuntimeException("Sauvegarde Impossible !!"));
	            }

	        //resultSet.close();
	        //instruction.close();
			String requeteLigue = "SELECT * FROM ligue";
			Statement instructionLigue = connection.createStatement();
			ResultSet ligues = instructionLigue.executeQuery(requeteLigue);

			while (ligues.next()) {

				gestionPersonnel.addLigue(ligues.getInt("idLigue"), ligues.getString("nom"));

				PreparedStatement req = connection.prepareStatement("SELECT * FROM employe WHERE idLigue = ?");

				req.setInt(1, ligues.getInt("idLigue"));
				ResultSet employe = req.executeQuery();
				Ligue ligue = gestionPersonnel.getLigues().last();

				while (employe.next()) {

					int id = employe.getInt("idEmploye");
					String nom = employe.getString("nom");
					String prenom = employe.getString("prenom");
					String mail = employe.getString("mail");
					String password = employe.getString("password");
					LocalDate date_arrivee = employe.getDate("dateArrivee") != null
							? LocalDate.parse(employe.getString("dateArrivee"))
							: null;
					LocalDate date_depart = employe.getDate("dateDepart") != null
							? LocalDate.parse(employe.getString("dateDepart"))
							: null;

					Employe employee = ligue.addEmploye(nom, prenom, mail, password, date_arrivee, date_depart, id);

				}
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
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
/*
	@Override
	public int insert(Employe employe) throws SauvegardeImpossible {
		try {
			PreparedStatement instruction;
			instruction = connection.prepareStatement(
					"INSERT INTO employe (nom, prenom, mail, password, dateArrivee, dateDepart,idLigue) VALUES (?,?,?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			instruction.setString(1, employe.getNom());
			instruction.setString(2, employe.getPrenom());
			instruction.setString(3, employe.getMail());
			instruction.setString(4, employe.getPassword());
			instruction.setDate(5, employe.getDateArrivee() == null ? null : Date.valueOf(employe.getDateArrivee()));
			instruction.setDate(6, employe.getDateDepart() == null ? null : Date.valueOf(employe.getDateDepart()));
			
			instruction.setInt(7, employe.getLigue().getId());
			instruction.executeUpdate();
			ResultSet id = instruction.getGeneratedKeys();
			id.next();

			return id.getInt(1);
		} catch (SQLException exception) {
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}
	}*/
	@Override
	public int insert(Employe employe) throws SauvegardeImpossible {
	    try {
	        PreparedStatement instruction;
	        if (employe.getLigue() != null) { // Si la ligue de l'employé est non null, ce n'est pas le root
	            instruction = connection.prepareStatement(
	                    "INSERT INTO employe (nom, prenom, mail, password, dateArrivee, dateDepart, idLigue) VALUES (?,?,?,?,?,?,?)",
	                    Statement.RETURN_GENERATED_KEYS);
	            instruction.setInt(7, employe.getLigue().getId());
	        } else { // Si la ligue de l'employé est null, c'est le root
	            instruction = connection.prepareStatement(
	                    "INSERT INTO employe (nom, prenom, mail, password, dateArrivee, dateDepart) VALUES (?,?,?,?,?,?)",
	                    Statement.RETURN_GENERATED_KEYS);
	        }
	        instruction.setString(1, employe.getNom());
	        instruction.setString(2, employe.getPrenom());
	        instruction.setString(3, employe.getMail());
	        instruction.setString(4, employe.getPassword());
	        instruction.setDate(5, employe.getDateArrivee() == null ? null : Date.valueOf(employe.getDateArrivee()));
	        instruction.setDate(6, employe.getDateDepart() == null ? null : Date.valueOf(employe.getDateDepart()));
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

	@Override
	public void update(Ligue ligue) throws SauvegardeImpossible {
		try {
			PreparedStatement instruction;
			instruction = connection.prepareStatement("UPDATE ligue SET nom = ? WHERE idLigue = ?");
			instruction.setString(1, ligue.getNom());
			instruction.setInt(2, ligue.getId());
			instruction.executeUpdate();

		} catch (SQLException exception) {
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}
	}

	@Override
	
	public void update(Employe employe) throws SauvegardeImpossible
	{
		try {
			PreparedStatement instruction;
			instruction = connection.prepareStatement("UPDATE employe SET nom = ?, prenom = ?, mail = ?, password= ?, dateArrivee = ? WHERE idEmploye  = ?");
			instruction.setString(1, employe.getNom());
			instruction.setString(2, employe.getPrenom());
			instruction.setString(3, employe.getMail());
			instruction.setString(4, employe.getPassword());
			instruction.setDate(5, employe.getDateArrivee() == null ? null : Date.valueOf(employe.getDateArrivee()));
			//instruction.setDate(6, employe.getDateDepart() == null ? null : Date.valueOf(employe.getDateDepart()));
			instruction.setInt(6, employe.getId());
			instruction.executeUpdate();
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new SauvegardeImpossible(e);
		}
	}
	@Override
	public void delete(Employe employe) throws SauvegardeImpossible {
		try {
			PreparedStatement instruction;
			instruction = connection.prepareStatement("DELETE FROM employe WHERE idEmploye = ? ");
			instruction.setInt(1, employe.getId());
			instruction.executeUpdate();

		} catch (SQLException exception) {
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}
	}
	
	@Override
	public void delete(Ligue ligue) throws SauvegardeImpossible {
		try {
			PreparedStatement instruction;
			instruction = connection.prepareStatement("DELETE FROM ligue WHERE idLigue = ?");
			instruction.setInt(1, ligue.getId());
			instruction.executeUpdate();

		} catch (SQLException exception) {
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}

	}
}
