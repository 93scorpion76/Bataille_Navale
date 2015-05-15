package controlers;

import view.GameView;
import view.SalonView;
import connectors.Player;
import connectors.Room;
import client.Client;

public class ConnexionControl {
	
	SalonView salon;
	Client cli = null;
	
	//Etablit une connexion au serveur
	public ConnexionControl(String namePlayer)
	{
		//cli = new Client("localhost",1234, namePlayer);
		
		Player myPlayer = cli.getPlayer();
		salon = new SalonView(myPlayer);
		
		ThreadClient tc = new ThreadClient(salon, cli, this);
		new Thread(tc).start();
	}
	
	public void changeView()
	{
		salon.dispose();
		new GameControl(cli);
	}

}
