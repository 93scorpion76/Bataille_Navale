package connectors;

import java.util.ArrayList;

import observer.Observable;
import observer.Observateur;
import view.RoomView;

public class Room{
		private int idRoom;
		private String name;
		private String creator;
		private int nbPlayerLife; // nb de joueur encore en vie. Permet de détecter la victoire. 
		private int nbPlayerMax; 
		private ArrayList<Player> lPlayer;
		private boolean roomFull;
		private int jeton; // Contient l'id du player qui a le jeton.
		
		public Room(int idRoom,String name,String creator, int nbPlayerMax)
		{
			lPlayer = new ArrayList<Player>();
			roomFull = false;
			this.idRoom = idRoom;
			nbPlayerLife = 0;
			this.nbPlayerMax = nbPlayerMax;
			jeton = -1;
			this.name = name;
			this.creator = creator;
		}
		
		public Room(int idRoom, String name, String creator, int nbPlayerLife, int nbPlayerMax, ArrayList<Player> lPlayer,boolean roomFull,int jeton)
		{
			this.idRoom = idRoom;
			this.nbPlayerLife = nbPlayerLife;
			this.nbPlayerMax = nbPlayerMax;
			this.lPlayer = lPlayer;
			this.roomFull = roomFull;
			this.jeton = jeton;
			this.name = name;
			this.creator = creator;
		}
		
		public boolean AddPlayer(Player p1)
		{
			if(!roomFull)
			{
				lPlayer.add(p1);
				nbPlayerLife = lPlayer.size();
				if(lPlayer.size()==nbPlayerMax)
					roomFull = true;
				return true;
			}
			else
				return false;
		}
		
		public int NextPlayer(int idPlayer)  // Renvoi l'id du prochain joueur en vie dans la liste.
		{
			ArrayList<Player> lPlayerLife = new ArrayList<Player>(); // Création d'un tablau répertoriant les joueurs en vie.
			for(int i=0; i<lPlayer.size();i++)
			{
				if(lPlayer.get(i).isLife())
					lPlayerLife.add(lPlayer.get(i));
			}
			
			int posJ = 0;
			for(int j=0;j<lPlayerLife.size();j++){
				if(lPlayerLife.get(j).getId() == idPlayer){
					if(j+1<lPlayerLife.size())
						posJ = j+1;
					j = lPlayerLife.size(); // sortie de boucle.
				}
			}
			if(lPlayerLife.size()<=0)
				return -1;
			else 
				return lPlayerLife.get(posJ).getId();
		}
		
		public void setRoomFull(boolean roomFull) {this.roomFull = roomFull;}		
		public int getIdRoom() {return idRoom;}
		public String getName(){return name;}
		public String getCreator(){return creator;}
		public int getNbPlayerMax(){return nbPlayerMax;}
		public int getNbPlayerLife(){return nbPlayerLife;}
		public int getNbPlayer(){return lPlayer.size();}
		public boolean isFull(){return roomFull;}
		public int getJeton(){return jeton;}
		public void setJeton(int idPlayer){jeton = idPlayer;}
		public boolean isFinish()
		{
			if(getNbPlayerLife() <= 1)
				return true;
			else 
				return false;
		}
		
		public Player getPlayerById(int idPlayer)
		{
			for(int i=0; i<lPlayer.size();i++)
			{
				if(lPlayer.get(i).getId() == idPlayer)
					return lPlayer.get(i);
			}
			return null;
		}
		
		public void removePlayerById(int idPlayer)
		{
			for(int i=0;i<lPlayer.size();i++)
			{
				if(lPlayer.get(i).getId() == idPlayer)
					lPlayer.remove(i);
			}
		}
		
		public Player getPlayer(int position)
		{
			return lPlayer.get(position);
		}
		
		public ArrayList<Integer> CheckShoot(int idPlayer, int posTir)
		{
			ArrayList<Integer> retour = new ArrayList<Integer>(); // Retour la liste des idJoueur morts ou 0 si aucun joueur touchés.
			
			for(int i=0;i<lPlayer.size();i++)
			{		
				if(lPlayer.get(i).getId()!=idPlayer)
				{
					if(lPlayer.get(i).getPosBateau() == posTir && lPlayer.get(i).isLife())
					{
						lPlayer.get(i).Kill();
						retour.add(lPlayer.get(i).getId());
					}
				}
			}
			return retour;
		}
		
		public boolean isStart()
		{
			// Si tous les players sont prêt alors la room est prête.
			boolean start = false;
			if(lPlayer.size() >= 2){
				start = true;
				for(int i=0;i<lPlayer.size();i++)
				{
					if(!lPlayer.get(i).isReady())
						start = false;
				}
			}
			
			return start;
		}

}