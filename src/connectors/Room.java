package connectors;

import java.util.ArrayList;

public class Room {
		private int idRoom;
		private int nbPlayerLife; // nb de joueur encore en vie. Permet de détecter la victoire. 
		private int nbPlayerMax; 
		private ArrayList<Player> lPlayer;
		private boolean roomFull;
		private int jeton; // Contient l'id du player qui a le jeton.
		
		public Room(int idRoom,int nbPlayerMax)
		{
			lPlayer = new ArrayList<Player>();
			roomFull = false;
			this.idRoom = idRoom;
			nbPlayerLife = 0;
			this.nbPlayerMax = nbPlayerMax;
			jeton = -1;
		}
		
		public Room(int idRoom, int nbPlayerLife, int nbPlayerMax, ArrayList<Player> lPlayer,boolean roomFull,int jeton)
		{
			this.idRoom = idRoom;
			this.nbPlayerLife = nbPlayerLife;
			this.nbPlayerMax = nbPlayerMax;
			this.lPlayer = lPlayer;
			this.roomFull = roomFull;
			this.jeton = jeton;
		}
		
		public boolean AddPlayer(Player p1)
		{
			if(!roomFull)
			{
				lPlayer.add(p1);
				nbPlayerLife = lPlayer.size();
				if(lPlayer.size()==4)
					roomFull = true;
				return true;
			}
			else
				return false;
		}
		
		public void setRoomFull(boolean roomFull) {roomFull = roomFull;}		
		public int getIdRoom() {return idRoom;}
		public int getNbPlayerMax(){return nbPlayerMax;}
		public int getNbPlayerLife(){return nbPlayerLife;}
		public int getNbPlayer(){return lPlayer.size();}
		public boolean isFull(){return roomFull;}
		public int getJeton(){return jeton;}
		public void setJeton(int idPlayer){jeton = idPlayer;}
		
		public Player getPlayerById(int idPlayer)
		{
			for(int i=0; i<lPlayer.size();i++)
			{
				if(lPlayer.get(i).getId() == idPlayer)
					return lPlayer.get(i);
			}
			return null;
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
			for(int i=0;i<lPlayer.size();i++)
			{
				if(lPlayer.get(i).isReady())
					start = true;
			}
			
			return start;
		}
}