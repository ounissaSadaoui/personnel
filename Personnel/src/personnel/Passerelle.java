package personnel;

public interface Passerelle 
{
	public GestionPersonnel getGestionPersonnel() throws SauvegardeImpossible, DateInvalide;
	public void sauvegarderGestionPersonnel(GestionPersonnel gestionPersonnel)  throws SauvegardeImpossible;
	public int insert(Ligue ligue) throws SauvegardeImpossible;
	public int insert(Employe employe) throws SauvegardeImpossible;
	public Employe getRoot(Employe root);
	public void update(Ligue ligue) throws SauvegardeImpossible;
	public void update(Employe employe) throws SauvegardeImpossible, DateInvalide;
	
    public void delete(Employe employe) throws SauvegardeImpossible;
    public void delete(Ligue ligue) throws SauvegardeImpossible;
    public void updateLigueAdministrateur(Ligue ligue, GestionPersonnel gestionPersonnel) throws SauvegardeImpossible;
}
