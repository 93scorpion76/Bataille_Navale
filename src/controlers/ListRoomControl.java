package controlers;

import observer.Observateur;
import connectors.Player;
import view.ListRoomView;
import client.Client;

public class ListRoomControl implements Runnable, Observateur{

	private Client cli;
	private ListRoomView roomView;
	private String namePlayer;
	
	public ListRoomControl(String name)
	{
		cli = new Client("localhost", 1234);

		namePlayer = name;

		roomView = new ListRoomView();
		roomView.addObservateur(this);
		
		if(cli.ListRoom().size() > 0)
			System.out.println("\n Nom de la room: "+cli.ListRoom().get(0).getName());
		
		roomView.refreshTable(cli.ListRoom());
		//run();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		while(cli.getPlayer() == null)
		{
			//System.out.print("\n"+cli.ListRoom().get(0).getName());
			roomView.refreshTable(cli.ListRoom());

			try 
			{
				Thread.sleep(33);
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
		Player player = null;
		if(string.equals("create")){
			player = cli.CreateRoom(namePlayer, "Pouet", 4);
			
		}
		else if(string.equals("join"))
		{
			player = cli.ConnexionRoom(namePlayer, Integer.parseInt(appel));
		}
		
		if(player != null){
			roomView.dispose();
			new ConnexionControl(player, cli);
		}
	}
}
