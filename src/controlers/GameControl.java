package controlers;

import connectors.Player;
import connectors.Room;
import observer.Observateur;
import client.Client;
import view.GameView;

public class GameControl implements Runnable, Observateur{
	
	private Player player;
	private GameView game;
	private Client cli;
	public GameControl(Client cli)
	{
		this.cli = cli;
		player = cli.getPlayer();
		this.game = new GameView(player);
		
		run();
	}
	
	
	
	@Override
	public void update(String string, String appel) {
		// TODO Auto-generated method stub
		if(string.equals("shoot"))
		{
			cli.Shoot(Integer.parseInt(appel));
		}
		else if(string.equals("exit"))
		{
			cli.Exit();
			game.dispose();
		}
	}



	@Override
	public void run() 
	{
		// TODO Auto-generated method stub
		Room room = cli.InfoRoom();
		this.game.addObservateur(this);
		
		room.setJeton(room.getPlayer(0).getId());
		int jeton = -1;
		while(!room.isFinish())
		{

			if(room.getJeton() != jeton)
			{
				jeton = room.getJeton();
				this.game.enabledAllButton();
				for(int i = 0; i < room.getNbPlayer(); i++)
				{
					if(room.getJeton() == room.getPlayer(i).getId())
					{
						i = room.getNbPlayer();
						
						this.game.enabledAllButtonIDPlayer(room.getJeton());
					}
				}
			}
			
			try 
			{
				Thread.sleep(17);
			} 
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	} 
}
