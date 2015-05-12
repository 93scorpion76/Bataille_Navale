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
		while(execute)
		{
			try{
				
				for(int s=0;s<lsock.size();s++)
				{
					// On démarre la partie. 		
					// Lecture de la méthode demandée par le client:
					InputStreamReader isr = new InputStreamReader(lsock.get(s).getInputStream());
					BufferedReader in = new BufferedReader(isr);
					
					JSONObject json = new JSONObject(in.readLine());
					String msg_client = (String) json.get("Methode");
					
					// Exécution de la méthode demandée	
					PrintWriter out = new PrintWriter(lsock.get(s).getOutputStream(),true);
					int idPlayer;
					JSONObject dataset = new JSONObject(); 
					switch(msg_client)
					{
						case "InfoRoom":		
							if(room.getNbPlayer() == room.getNbPlayerMax()){
								// est-ce que tous le joueurs sont prêt ? 
								if(!room.isStart())
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
								dataset.put("lifePlayer"+i,room.getPlayer(i).isLife());
								dataset.put("readyPlayer"+i,room.getPlayer(i).isReady());
							}	
							dataset.put("roomFull",room.isFull());
							dataset.put("jeton",room.getJeton());
							out.println(dataset);
						break;
					
						case "SelectPosition":
							// Lecture de l'idPlayer et de la posBateau. 
							idPlayer = Integer.parseInt(json.getString("idPlayer"));
							int posBateau = Integer.parseInt(json.getString("posBateau"));
							room.getPlayerById(idPlayer).setPosBateau(posBateau);
							room.getPlayerById(idPlayer).setReady(true);
							// Changement du jeton. 
							if(idPlayer != room.getPlayer(room.getNbPlayerMax()-1).getId())
								room.setJeton(idPlayer+1); 
							else // Sinon, tout le monde a sélect sa pos, on redonne le jeton au premier. 
								room.setJeton(room.getPlayer(0).getId());
							
							out.println("OK");
						break;
					
						case "Shoot":
							// Lecture de l'idPlayer et de la posTir. 
							idPlayer = Integer.parseInt(json.getString("IdPlayer"));
							int posTir = Integer.parseInt(json.getString("posTir"));
							
							room.CheckShoot(idPlayer,posTir);
							
							// Préparation de la réponse: 
							// Liste des id des joueurs touchés
							try {
								ArrayList<Integer> lShoot = room.CheckShoot(idPlayer, posTir);
								dataset.put("NbPlayerDead",lShoot.size());
								for(int i=0;i<lShoot.size();i++){
									dataset.put("PlayerDead"+i, lShoot.get(i));
								}	
							} catch (JSONException e) {System.out.println("Erreur JSON client:"+e.getMessage());}
							
							System.out.println(dataset);
							out.println(dataset);
							
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

				/*// Lecture de la méthode demandée par le client:
				InputStreamReader isr = new InputStreamReader(lsock.get(jeton).getInputStream());
				BufferedReader in = new BufferedReader(isr);
				
				JSONObject json = new JSONObject(in.readLine());
				String msg_client = (String) json.get("Methode");
				
				// Exécution de la méthode demandée	
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
						
						// Préparation de la réponse: 
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
