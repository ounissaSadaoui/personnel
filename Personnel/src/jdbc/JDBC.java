package jdbc;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import personnel.*;

public class JDBC implements Passerelle 
{
	Connection connection;

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
	public GestionPersonnel getGestionPersonnel() throws SauvegardeImpossible 
	{
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
	          System.out.println("Un super utilisateur existe déjà");
	        }
	        
	        // Si l'employé n'existe pas, on insère les données
	        PreparedStatement instruction;
	        instruction = connection.prepareStatement("INSERT INTO employe (nom) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
	        instruction.setString(1, employe.getNom());
	        instruction.executeUpdate();
	        ResultSet id = instruction.getGeneratedKeys();
	        id.next();
	        return id.getInt(1);
	    } catch (SQLException exception) {
	        exception.printStackTrace();
	        throw new SauvegardeImpossible(exception);
	    }
	}

}
