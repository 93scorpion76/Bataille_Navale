package controlers;

import java.util.ArrayList;

import connectors.Player;
import connectors.Room;
import observer.Observateur;
import client.Client;
import view.GameView;

public class GameControl implements Runnable, Observateur{
	
	private Player player;
	private GameView game;
	private Client cli;
	private Room room;
	
	private String message;
	public GameControl(Client cli)
	{
		this.message = "";
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
			message = appel;
			ArrayList<Integer> listID = cli.Shoot(Integer.parseInt(appel));
			
			if(listID.size() == 0)
			{
				this.game.getActionButton(Integer.parseInt(appel)).changeInvisible();
			}
			else
			{
				this.game.getActionButton(Integer.parseInt(appel)).changeDebris();
			}
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
		this.room = cli.InfoRoom();
		this.game.addObservateur(this);
		
		int jeton = -1;
		while(!room.isFinish())
		{
			this.room = cli.InfoRoom();
			
			if(room.getJeton() != jeton)
			{
				if(!cli.LastAction().equals(""))
					this.game.addMessage("\n"+cli.LastAction());

				this.game.getTourLab().setText("C'est le tour du joueur "+room.getPlayerById(room.getJeton()).getNom());
				
				
				jeton = room.getJeton();
				this.game.enabledAllButton();
				for(int i = 0; i < room.getNbPlayer(); i++)
				{
					if(room.getJeton() == room.getPlayer(i).getId())
					{
						i = room.getNbPlayer();
						if(!this.game.getButtonEnabled())
							this.game.enabledAllButtonIDPlayer(room.getJeton());
					}
				}
			}
			
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
		
		if(room.isFinish())
		{
			this.game.enabledAllButton();
			this.game.addMessage("Game Finish");
		}
	
	} 
}
