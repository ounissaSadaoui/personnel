package personnel;

public interface Passerelle 
{
	public GestionPersonnel getGestionPersonnel() throws SauvegardeImpossible, DateInvalide;
	public void sauvegarderGestionPersonnel(GestionPersonnel gestionPersonnel)  throws SauvegardeImpossible;
	public int insert(Ligue ligue) throws SauvegardeImpossible;
	public int insert(Employe employe) throws SauvegardeImpossible;
	public Employe getRoot(Employe root);
	public void updateLigue (Ligue ligue) throws SauvegardeImpossible;
}
