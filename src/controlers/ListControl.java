package controlers;

import view.ListRoomView;
import client.Client;

public class ListControl {

	private Client cli;
	private ListRoomView roomView;
	private String namePlayer;
	
	public ListControl(String name)
	{
		cli = new Client("localhost", 1234);

		namePlayer = name;
		roomView = new ListRoomView(namePlayer);
		
		ListRoomControl tc = new ListRoomControl(namePlayer, roomView, cli, this);
		new Thread(tc).start();
	}
}
