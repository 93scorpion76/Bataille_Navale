package test;

import connectors.Player;
import connectors.Room;
import client.Client;

public class TestClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Client c1 = new Client("localhost",1234,"Romain");
		Client c2 = new Client("localhost",1234,"Valentin");
	
		Player p1 = c1.getPlayer();
		Player p2 = c2.getPlayer();
		
		System.out.println("L'id du client 1 est: "+p1.getId());
		System.out.println("L'id du client 2 est: "+p2.getId());
		
		Room r1 = c1.InfoRoom();
		Room r2 = c2.InfoRoom();
		if(r1 != null)
		{
			if(r1.getJeton() == p1.getId())
				System.out.println("C'est au joueur 1 de parler !!!!");
			else if(r2.getJeton() == p2.getId())
				System.out.println("C'est au joueur 2 de parler !!!");
			else
				System.out.println("Tous le monde est en attente...");
		}
		else
			System.out.println("Erreur réception JSON.");
	}

}
