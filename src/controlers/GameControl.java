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
			
			/*if(listID.size() == 0)
				message = "Le joueur "+room.getPlayerById(room.getJeton()).getNom()+ " a mis dans l'eau";
			else
			{
				String playerTouch = "";
				for(int i = 0; i < listID.size(); i++)
				{
					playerTouch += room.getPlayerById(listID.get(i)).getNom();
					if(i+1 < listID.size())
						playerTouch += ", ";
				}

				message = "Le joueur "+room.getPlayerById(room.getJeton()).getNom()+ " a touché "+playerTouch+" .";
			}*/
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
				if(!message.equals(""))
				{
					if(room.CheckShoot(jeton, Integer.parseInt(message)).size() > 0)
					{
						ArrayList<Integer> listID = room.CheckShoot(jeton, Integer.parseInt(message));
						String playerTouch = "";
						for(int i = 0; i < listID.size(); i++)
						{
							playerTouch += room.getPlayerById(listID.get(i)).getNom();
							if(i+1 < listID.size())
								playerTouch += ", ";
						}

						this.game.addMessage("Le joueur "+room.getPlayerById(jeton).getNom()+ " a touché "+playerTouch+" .");
					}
					else
					{
						this.game.addMessage("\nLe joueur "+room.getPlayerById(jeton).getNom()+ " a mis dans l'eau");
					}	
				}
				
				this.game.addMessage("\nC'est le tour du joueur "+room.getPlayerById(room.getJeton()).getNom());
				
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
				this.message = "";
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
	
	} 
}
