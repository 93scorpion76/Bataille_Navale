package controlers;

import view.GameView;
import view.RoomView;
import connectors.Player;
import connectors.Room;
import client.Client;

public class ConnexionControl {
	
	RoomView roomView;
	Client cli = null;
	
	//Etablit une connexion au serveur
	public ConnexionControl(Player player, Client cli)
	{
		//cli = new Client("localhost",1234, namePlayer);
		this.cli = cli;
		Player myPlayer = player;
		
		roomView = new RoomView(myPlayer);
		
		ThreadClient tc = new ThreadClient(roomView, cli, this);
		new Thread(tc).start();
	}
	
	public void changeView()
	{
		roomView.dispose();
		new GameControl(cli);
	}

}
