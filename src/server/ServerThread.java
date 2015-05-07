package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import connectors.Room;

public class ServerThread implements Runnable {

	private ArrayList<Socket> lsock;
	private Room room;

	public ServerThread(Room room) {
		lsock = new ArrayList<Socket>();
		this.room = room;
	}
	
	public void AddSocket(Socket socket)
	{
		lsock.add(socket);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		boolean execute = true;
		boolean part_start = false;
		while(execute)
		{
			try{
				
				for(int s=0;s<lsock.size();s++)
				{
					// On d�marre la partie. 		
					// Lecture de la m�thode demand�e par le client:
					InputStreamReader isr = new InputStreamReader(lsock.get(s).getInputStream());
					BufferedReader in = new BufferedReader(isr);
					
					JSONObject json = new JSONObject(in.readLine());
					String msg_client = (String) json.get("Methode");
					
					// Ex�cution de la m�thode demand�e	
					PrintWriter out = new PrintWriter(lsock.get(s).getOutputStream(),true);
					int idPlayer;
					JSONArray ljson = new JSONArray();
					JSONObject dataset = new JSONObject(); 
					switch(msg_client)
					{
						case "InfoRoom":
							if(room.getNbPlayer() == room.getNbPlayerMax() && part_start == false){
								part_start = true;
								room.setJeton(room.getPlayer(0).getId()); // On donne le premier jeton ! 
							}
							// Reponse
							dataset.put("idRoom",room.getIdRoom());
							dataset.put("nbPlayerLife",room.getNbPlayerLife());
							dataset.put("nbPlayerMax",room.getNbPlayerMax());
							dataset.put("nbPlayer",room.getNbPlayer());
							for(int i=0;i<room.getNbPlayer();i++)
							{
								dataset.put("idPlayer"+i,room.getPlayer(i).getId());
								dataset.put("nomPlayer"+i,room.getPlayer(i).getNom());
								dataset.put("lifePlayer"+i,room.getPlayer(i).getLife());
								dataset.put("readyPlayer"+i,room.getPlayer(i).getReady());
							}	
							dataset.put("roomFull",room.roomIsFull());
							dataset.put("jeton",room.getJeton());
							ljson.put(dataset);
							out.println(ljson);
						break;
					
						case "SelectPosition":
							// Lecture de l'idPlayer et de la posBateau. 
							idPlayer = Integer.parseInt(json.getString("IdPlayer"));
							int posBateau = Integer.parseInt(json.getString("posBateau"));
							room.getPlayer(idPlayer).setPosBateau(posBateau);
							out.println("OK");
							
							// Changement du jeton. 
							if(idPlayer != room.getNbPlayerMax()-1)
								room.setJeton(idPlayer+1); 
							else // Sinon, tout le monde a s�lect sa pos, on redonne le jeton au premier. 
								room.setJeton(room.getPlayer(0).getId());
						break;
					
						case "Shoot":
							// Lecture de l'idPlayer et de la posTir. 
							idPlayer = Integer.parseInt(json.getString("IdPlayer"));
							int posTir = Integer.parseInt(json.getString("posTir"));
							
							room.CheckShoot(idPlayer,posTir);
							
							// Pr�paration de la r�ponse: 
							try {
								dataset.put("Code", 1);
								ljson.put(dataset);
							} catch (JSONException e) {System.out.println("Erreur JSON client:"+e.getMessage());}
							
							System.out.println(ljson);
							out.println(ljson);
							
							if(idPlayer != room.getNbPlayerMax()-1)
								room.setJeton(idPlayer+1);
							else
								room.setJeton(room.getPlayer(0).getId());
						break;
						
						case "Exit":
							System.out.println("Fermeture du serveur pour le port:"+lsock.get(s).getLocalPort());
							out.println("Tchao");
							out.close();
							lsock.get(s).close();		
							execute = false;
							break;
					}
				}

				
				/*// Lecture de la m�thode demand�e par le client:
				InputStreamReader isr = new InputStreamReader(lsock.get(jeton).getInputStream());
				BufferedReader in = new BufferedReader(isr);
				
				JSONObject json = new JSONObject(in.readLine());
				String msg_client = (String) json.get("Methode");
				
				// Ex�cution de la m�thode demand�e	
				PrintWriter out = new PrintWriter(sock.getOutputStream(),true);
				int idPlayer;
				JSONArray ljson = new JSONArray();
				switch(msg_client)
				{
					case "SelectPosition":
						// Lecture de l'idPlayer et de la posBateau. 
						idPlayer = Integer.parseInt(json.getString("IdPlayer"));
						int posBateau = Integer.parseInt(json.getString("posBateau"));
						room.getPlayer(idPlayer).setPosBateau(posBateau);
						out.println("OK");
					break;
				
					case "Shoot":
						// Lecture de l'idPlayer et de la posTir. 
						idPlayer = Integer.parseInt(json.getString("IdPlayer"));
						int posTir = Integer.parseInt(json.getString("posTir"));
						
						room.CheckShoot(idPlayer,posTir);
						
						// Pr�paration de la r�ponse: 
						try {
							JSONObject dataset = new JSONObject();
							dataset.put("Code", 1);
							ljson.put(dataset);
						} catch (JSONException e) {System.out.println("Erreur JSON client:"+e.getMessage());}
						
						System.out.println(ljson);
						out.println(ljson);
					break;
					
					default:
						System.out.println("Fermeture du serveur pour le port:"+sock.getLocalPort());
						out.println("Tchao");
						out.close();
						sock.close();		
						execute = false;
						break;
				}*/
			}
			catch(Exception e){}	
		}
	}
}
