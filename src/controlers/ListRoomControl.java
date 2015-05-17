package controlers;

import observer.Observateur;
import connectors.Player;
import view.CreateView;
import view.ListRoomView;
import client.Client;

public class ListRoomControl implements Runnable, Observateur{

	private Client cli;
	private ListRoomView roomView;
	private String namePlayer;
	private ListControl listControl;
	private boolean isUpdate = false;
	
	/*public ListRoomControl(String name)
	{
		cli = new Client("localhost", 1234);

		namePlayer = name;
		roomView = new ListRoomView(namePlayer);
		
		run();
	}*/

	public ListRoomControl(String namePlayer, ListRoomView roomView, Client cli,
			ListControl listControl) {
		// TODO Auto-generated constructor stub
		
		this.namePlayer = namePlayer;
		this.cli = cli;
		this.roomView = roomView;
		this.listControl = listControl;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.roomView.addObservateur(this);
		while(cli.getPlayer() == null)
		{
			if(!isUpdate)
				this.roomView.refreshTable(cli.ListRoom());

			try 
			{
				Thread.sleep(1000);
			} 
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(String string, String appel) {
		// TODO Auto-generated method stub
		isUpdate = true;
		
		Player player = null;
		if(string.equals("create")){
			new CreateView(namePlayer, cli);
		}
		else if(string.equals("join"))
		{
			player = cli.ConnexionRoom(namePlayer, Integer.parseInt(appel));
			if(player != null){
				new ConnexionControl(player, cli);
			}
		}
		isUpdate = false;
		roomView.dispose();
	}
}
