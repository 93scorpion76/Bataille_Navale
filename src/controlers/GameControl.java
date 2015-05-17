package controlers;

import java.sql.Date;
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
	private boolean isUpdate = false;
	
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
		isUpdate = true;
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
			
			isUpdate = false;
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
		int []boat = new int[3];
		int []debris = new int[3];
		int indexBoat = 0;
		int indexDebris = 0;
		
		while(!room.isFinish())
		{
			if(!isUpdate){
				this.room = cli.InfoRoom();
			
				if(room.getJeton() != jeton)
				{
					if(!cli.LastAction().equals(""))
						this.game.addMessage("\n"+cli.LastAction());
					
					jeton = room.getJeton();
					
					if(player.getId() != jeton)
						this.game.getTourLab().setText("C'est le tour du joueur "+room.getPlayerById(room.getJeton()).getNom());
					else
						this.game.getTourLab().setText("C'est à votre tour de jouer !");
				
					jeton = room.getJeton();
					this.game.enabledAllButton();
					
					indexDebris = 0;
					indexBoat = 0;
					for(int i = 0; i <  room.getNbPlayer(); i++)
					{
						if(room.getPlayer(i).isLife()){
							boat[indexBoat] = room.getPlayer(i).getPosBateau();
							indexBoat++;	
						}		
						else
						{
							debris[indexDebris] = room.getPlayer(i).getPosBateau();
							indexDebris++;
						}
					}
					
					for(int i = 0; i < room.getNbPlayer(); i++)
					{
						if(room.getJeton() == room.getPlayer(i).getId())
						{
							i = room.getNbPlayer();
							if(!this.game.getButtonEnabled())
								this.game.enabledAllButtonIDPlayer(room.getJeton());
						}
						else if(!room.getPlayer(i).isLife())
						{
							this.game.Observateur(boat, debris);
						}
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
			this.game.addMessage("\n"+cli.LastAction());
			this.game.enabledAllButton();
			int timeWait  = 7;
			
			String nameWinner ="";
			for(int i = 0; i < room.getNbPlayer(); i++)
			{
				if(room.getPlayer(i).isLife())
					nameWinner = room.getPlayer(i).getNom();
			}
			
			this.game.addMessage("\nLe joueur "+nameWinner+" a gagné !");
			this.game.addMessage("\nRetour a la liste des batailles dans "+timeWait+" secondes.\n\n");
			
			float date = 0;
			boolean affiche = true;
			String var = "";
			int tmpSec;
			
			while(date <= timeWait)
			{
				date += 0.033;
				
				try 
				{
					Thread.sleep(33);
				} 
				catch (InterruptedException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				tmpSec = (int) ((int)timeWait - date);
				if(var.indexOf(tmpSec+"") == -1)
				{
					affiche = true;
				}
				
				if(affiche){
					var += tmpSec;
					this.game.addMessage(tmpSec+"... ");
					affiche = false;
				}
				
			}
			
			cli.Exit();
			game.dispose();
			new ListControl(player.getNom());
		}
	
	} 
}
