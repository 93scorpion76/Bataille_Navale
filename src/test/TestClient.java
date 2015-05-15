package test;

import connectors.Player;
import connectors.Room;
import client.Client;

// !!!!!!! LE CLIENT QUI REJOINT LA ROOM DU TESTCLIENTCREATOR. A LANCER APRES LE TESTCLIENTCREATOR. !!!!!!

public class TestClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Client c1 = new Client("localhost",1234);
		Player p1 = c1.ConnexionRoom("Toto", 1);
		
		System.out.println("L'id du client est: "+p1.getId());
		Room r1 = c1.InfoRoom();
		c1.SelectPosition(4);
		System.out.println("SELECTION DE LA POSITION BATEAU: OK !!!!");
		
		while(true)
		{
			r1 = c1.InfoRoom();
			if(r1 != null)
			{
				if(r1.getJeton() == p1.getId()){	
					c1.Shoot(5);
					System.out.println("Le client Toto a tiré en 5");	
					break;
				}
				else if(r1.getJeton() != -1)
					System.out.println("En attente... du joueur:"+r1.getPlayerById(r1.getJeton()).getNom());
				else
					System.out.println("En attente de joueurs sur la room n°"+r1.getIdRoom());
			}
			else
				System.out.println("Erreur réception JSON.");
		}
		
		c1.Exit();
	}

}
