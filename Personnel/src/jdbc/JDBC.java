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
		        } else {
		            gestionPersonnel.addRoot();
		        }
	
		        // fin de la requete pour le root
		        resultSet.close();
		        instruction.close();
	
		        // Requete pour récupérer les ligues
		        String requeteLigue = "SELECT * FROM ligue";
		        Statement instructionLigue = connection.createStatement();
		        ResultSet ligues = instructionLigue.executeQuery(requeteLigue);
	
		        while (ligues.next()) {
		            int ligueId = ligues.getInt("idLigue");
		            String ligueNom = ligues.getString("nom");
		            gestionPersonnel.addLigue(ligueId, ligueNom);
	
		            // récupération des employés de la ligue actuelle
		            PreparedStatement req = connection.prepareStatement("SELECT * FROM employe WHERE idLigue = ?");
		            req.setInt(1, ligueId);
		            ResultSet employesResultSet = req.executeQuery();
		            Ligue ligue = null;
	
		            // on s'assure qu'on est bien sur la bonne ligue 
		            
		            for (Ligue l : gestionPersonnel.getLigues()) {
		                if (l.getId() == ligueId) {
		                    ligue = l;
		                    break;
		                }
		            }
	
		            if (ligue == null) {
		                throw new RuntimeException("Cette ligue n'existe pas : " + ligueId);
		            }
	                //parcours des employes de la ligue
		            while (employesResultSet.next()) {
		                int employeId = employesResultSet.getInt("idEmploye");
		                String employeNom = employesResultSet.getString("nom");
		                String employePrenom = employesResultSet.getString("prenom");
		                String employeMail = employesResultSet.getString("mail");
		                String employePassword = employesResultSet.getString("password");
		                LocalDate dateArrivee = employesResultSet.getDate("dateArrivee") != null ?
		                        employesResultSet.getDate("dateArrivee").toLocalDate() : null;
		                LocalDate dateDepart = employesResultSet.getDate("dateDepart") != null ?
		                        employesResultSet.getDate("dateDepart").toLocalDate() : null;
	
		                Employe employe = ligue.addEmploye(employeNom, employePrenom, employeMail, employePassword,
		                        dateArrivee, dateDepart, employeId);
	
		                // vérification de si l'employé est l'admin
		                if (ligues.getInt("idEmploye") == employeId) {
		                    ligue.setAdministrateur(employe);
		                }
		            }
		            employesResultSet.close();
		            req.close();
		        }
	
		        // fermeture finale
		        ligues.close();
		        instructionLigue.close();
	
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
		        // Supprimer les employés associés à la ligue
		        PreparedStatement deleteEmployes = connection.prepareStatement("DELETE FROM employe WHERE idLigue = ?");
		        deleteEmployes.setInt(1, ligue.getId());
		        deleteEmployes.executeUpdate();
		        deleteEmployes.close();

		        // Supprimer la ligue elle-même
		        PreparedStatement deleteLigue = connection.prepareStatement("DELETE FROM ligue WHERE idLigue = ?");
		        deleteLigue.setInt(1, ligue.getId());
		        deleteLigue.executeUpdate();
		        deleteLigue.close();

		        System.out.println("Ligue supprimée avec succès.");

		    } catch (SQLException exception) {
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
		
		//
		public boolean authenticateUser(String username, String password) {
		    String query = "SELECT password, idLigue FROM employe WHERE nom = ? ";
		    try (PreparedStatement statement = connection.prepareStatement(query)) {
		        statement.setString(1, username);
		        ResultSet resultSet = statement.executeQuery();
		        if (resultSet.next()) {
		            String storedPassword = resultSet.getString("password");
		            return password.equals(storedPassword);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return false;
		}
		
	
	
	}
