package test;

import connectors.Player;
import connectors.Room;
import client.Client;

public class TestClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Client c1 = new Client("localhost",1234,"Romain");
	
		Player p1 = c1.getPlayer();
		
		System.out.println("L'id du client est: "+p1.getId());
		

		while(true)
		{
			Room r1 = c1.InfoRoom();	

			if(r1 != null)
			{
				if(r1.getJeton() == p1.getId()){	
					c1.SelectPosition(2);
					System.out.println("SELECTION DE LA POSITION BATEAU: OK !!!!");
					break;
				}
				else if(r1.getJeton() != -1)
					System.out.println("En attente... du joueur:"+r1.getPlayerById(r1.getJeton()).getNom());
				else
					System.out.println("En attente de joueurs. Room:"+r1.getIdRoom());
			}
			else
				System.out.println("Erreur réception JSON.");
			c1.Exit();
		}
	}

}
