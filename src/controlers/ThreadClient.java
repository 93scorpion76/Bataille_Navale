package controlers;

import observer.Observable;
import observer.Observateur;
import client.Client;
import connectors.Room;
import view.RoomView;

public class ThreadClient implements Runnable, Observateur{

	private RoomView salon;
	private Client cli;
	private ConnexionControl connexionControl;
	
	public ThreadClient(RoomView salon, Client cli, ConnexionControl connexionControl)
	{
		this.salon = salon;
		this.cli = cli;
		this.connexionControl = connexionControl;
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Room room = cli.InfoRoom();
		salon.addObservateur(this);
		
		while(!room.isStart())
		{
			if(!cli.getSock().isClosed()){

				room = cli.InfoRoom();
			
			
				for(int i = 0; i < room.getNbPlayer(); i++)
				{
					room = cli.InfoRoom();
			
					salon.setPlayerName(i,room.getPlayer(i));
					
					if(room.getPlayer(i).isReady())
						salon.setStatut(i, true);
					
				}
				
				try {
					Thread.sleep(33);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
			else
				break;
		}
		
		if(!cli.getSock().isClosed()){
			if(room.isStart())
			{
				connexionControl.changeView();
			}
		}
	}

	@Override
	public void update(String string, String appel) {
		// TODO Auto-generated method stub
		
		if(string.equals("choosePosition")){
			cli.SelectPosition(Integer.parseInt(appel));
		}
		else if(string.equals("exit")){
			cli.Exit();
			salon.dispose();
		}

		
	}


}
