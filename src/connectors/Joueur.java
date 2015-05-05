package connectors;

public class Joueur {

	private int Id; 
	private String Nom;
	private int PosBateau;
	
	public Joueur(int id, String nom)
	{
		Id = id; 
		Nom = nom;
	}
	
	public int getPosBateau() {
		return PosBateau;
	}

	public void setPosBateau(int posBateau) {
		PosBateau = posBateau;
	}

	public int getId() {
		return Id;
	}

	public String getNom() {
		return Nom;
	}
	
	public void Tirer(int posTir)
	{
		// Requête vers serveur en lui passant idJoueur + posTir
		
	}
	
}
	

