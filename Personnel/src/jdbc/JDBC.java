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
	public GestionPersonnel getGestionPersonnel() throws SauvegardeImpossible {
	    GestionPersonnel gestionPersonnel = new GestionPersonnel();
	    try {

			String requeteLigues = "select * from ligue";
			Statement instructionLigues = connection.createStatement();
			ResultSet ligues = instructionLigues.executeQuery(requeteLigues);
			while (ligues.next())
				try {
					gestionPersonnel.addLigue(ligues.getInt(1), ligues.getString(2));
				} catch (SauvegardeImpossible e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	
	    	
	        // Requête SQL pour sélectionner les informations du root depuis la base de données
	        String requete = "SELECT * FROM employe WHERE nom = 'root'";
	        PreparedStatement instruction = connection.prepareStatement(requete);
	        ResultSet resultSet = instruction.executeQuery();

	        if (resultSet.next()) {
	            String nom = resultSet.getString("nom");
	            String password = resultSet.getString("password");

	            // création de l'instance d'employé pour le root
	            Employe root = new Employe(gestionPersonnel, -1, null, nom, "", password, "", null, null);

	            // Affecter le root à la variable d'instance root de la classe GestionPersonnel
	            gestionPersonnel.root = root;
	        } else {
	            // cas ou le root n'existe pas en bididi, on le créé
	            String requeteInsert = "INSERT INTO employe (nom, password) VALUES ('root', 'toor')";
	            PreparedStatement instructionInsert = connection.prepareStatement(requeteInsert);
	            instructionInsert.executeUpdate();
	            instructionInsert.close();

	            // Ensuite, on le lit
	            resultSet = instruction.executeQuery();
	            if (resultSet.next()) {
	                String nom = resultSet.getString("nom");
	                String password = resultSet.getString("password");

	                Employe root = new Employe(gestionPersonnel, -1, null, nom, "", password, "", null, null);

	                // Affecter le root à la variable d'instance root de la classe GestionPersonnel
	                gestionPersonnel.root = root;
	            } else {
	                throw new SauvegardeImpossible(new RuntimeException("Sauvegarde Impossible !!"));
	            }
	        }

	        resultSet.close();
	        instruction.close();
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

	@Override
	public void updateLigue(Ligue ligue) throws SauvegardeImpossible {
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

}
