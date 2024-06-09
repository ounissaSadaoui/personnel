package jdbc;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;

import org.mindrot.jbcrypt.BCrypt;

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
	public int insert(Ligue ligue) throws SauvegardeImpossible {
	    try {
	        PreparedStatement instruction;
	        instruction = connection.prepareStatement(
	        		//la requete avec une sous requete met l'id du root comme idEmploye pour la ligue à son insertion
	                "insert into ligue (nom, idEmploye) select ?, idEmploye from employe where idLigue IS NULL",
	                Statement.RETURN_GENERATED_KEYS);
	        instruction.setString(1, ligue.getNom());
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
	public int insert(Employe employe) throws SauvegardeImpossible {
		try {
			PreparedStatement instruction;
			instruction = connection.prepareStatement(
					"INSERT INTO employe (nom, prenom, mail, password, dateArrivee, dateDepart, idLigue) VALUES (?,?,?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			instruction.setString(1, employe.getNom());
			instruction.setString(2, employe.getPrenom());
			instruction.setString(3, employe.getMail());
	        instruction.setString(4, employe.getPassword());
			instruction.setDate(5, employe.getDateArrivee() == null ? null : Date.valueOf(employe.getDateArrivee()));
			instruction.setDate(6, employe.getDateDepart() == null ? null : Date.valueOf(employe.getDateDepart()));
			//cas root, ou il n'a pas de ligue associéé
			if (employe.getLigue() == null) {
	            instruction.setNull(7, Types.INTEGER);
	        } else {//cas autre employe, avec une ligue
	            System.out.print("cas avec ligue");

	            instruction.setInt(7, employe.getLigue().getId());
	        }
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
		// are you sure ? c'était en todo ici, regarde bien si pas de problèmes après ça
		return root;
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
			instruction = connection.prepareStatement("UPDATE employe SET nom = ?, prenom = ?, mail = ?, password= ?, dateArrivee = ?, dateDepart = ? WHERE idEmploye  = ?");
			instruction.setString(1, employe.getNom());
			instruction.setString(2, employe.getPrenom());
			instruction.setString(3, employe.getMail());
			instruction.setString(4, employe.getPassword());
			instruction.setDate(5, employe.getDateArrivee() == null ? null : Date.valueOf(employe.getDateArrivee()));
			instruction.setDate(6, employe.getDateDepart() == null ? null : Date.valueOf(employe.getDateDepart()));
			instruction.setInt(7, employe.getId());
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
	        // Supprimer les employés de la ligue
	        PreparedStatement deleteEmployes = connection.prepareStatement("DELETE FROM employe WHERE idLigue = ?");
	        deleteEmployes.setInt(1, ligue.getId());
	        deleteEmployes.executeUpdate();

	        // Supprimer la ligue
	        PreparedStatement deleteLigue = connection.prepareStatement("DELETE FROM ligue WHERE idLigue = ?");
	        deleteLigue.setInt(1, ligue.getId());
	        deleteLigue.executeUpdate();

	        System.out.println("Ligue supprimée avec succès.");

	    } catch (SQLException exception) {
	        exception.printStackTrace();
	        throw new SauvegardeImpossible(exception);
	    }
	}
	@Override
	public void updateLigueAdministrateur(Ligue ligue, GestionPersonnel gestionPersonnel) throws SauvegardeImpossible {
	    String query = "UPDATE ligue SET idEmploye = ? WHERE idLigue = ?";
	    try (PreparedStatement statement = connection.prepareStatement(query)) {
	        // Si un administrateur est spécifié pour la ligue, utilisez son ID
	        if (ligue.getAdministrateur() != null) {
	            statement.setInt(1, ligue.getAdministrateur().getId());
	        } else {
	            // Si aucun administrateur n'est spécifié, utilisez l'ID du root
	            Employe root = gestionPersonnel.getRoot();
	            statement.setInt(1, root.getId());
	        }
	        statement.setInt(2, ligue.getId());
	        statement.executeUpdate();
	    } catch (SQLException e) {
	        throw new SauvegardeImpossible(e);
	    }
	}
	


}
