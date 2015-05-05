package connectors;

public class Player {

	private int Id; 
	private String Nom;
	private int PosBateau;
	
	public Player(int id, String nom)
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
}
	

