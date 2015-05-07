package connectors;

public class Player {

	private int Id; 
	private String Nom;
	private int PosBateau;
	private boolean life;
	private boolean ready;
	
	public Player(int id, String nom)
	{
		Id = id; 
		Nom = nom;
		life = true;
		ready = false;
		PosBateau = 0;
	}
	
	public Player(int id,String nom,boolean life, boolean ready)
	{
		this.Id = id; 
		this.Nom = nom; 
		this.life = life;
		this.ready = ready;
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
	
	public void Kill()
	{
		life = false;
	}
	
	public boolean getLife()
	{
		return life;
	}
	
	public boolean isReady(){return ready;}
	public void setReady(boolean r){ready = r;}
	
}
	

