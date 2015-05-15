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
	private String lastAction = "";
	private boolean execute = true;

	public ServerThread(Room room) {
		lsock = new ArrayList<Socket>();
		this.room = room;
	}
	
	public void AddSocket(Socket socket)
	{
		lsock.add(socket);
	}

	public boolean isExecute(){return execute;}
	public Room getRoom(){return room;}
	@Override
	public void run() {
		// TODO Auto-generated method stub
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
					String msg_client = (String) json.get("methode");
					
					// Exécution de la méthode demandée	
					PrintWriter out = new PrintWriter(lsock.get(s).getOutputStream(),true);
					int idPlayer;
					JSONObject dataset = new JSONObject(); 
					switch(msg_client)
					{
						case "infoRoom":		
							// Reponse
							dataset.put("idRoom",room.getIdRoom());
							dataset.put("nameRoom",room.getName());
							dataset.put("creatorRoom", room.getCreator());
							dataset.put("nbPlayerLife",room.getNbPlayerLife());
							dataset.put("nbPlayerMax",room.getNbPlayerMax());
							dataset.put("nbPlayer",room.getNbPlayer());
							for(int i=0;i<room.getNbPlayer();i++)
							{
								dataset.put("idPlayer"+i,room.getPlayer(i).getId());
								dataset.put("namePlayer"+i,room.getPlayer(i).getNom());
								dataset.put("lifePlayer"+i,room.getPlayer(i).isLife());
								dataset.put("readyPlayer"+i,room.getPlayer(i).isReady());
							}	
							dataset.put("roomFull",room.isFull());
							dataset.put("jeton",room.getJeton());
							out.println(dataset);
						break;
					
						case "selectPosition":
							// Lecture de l'idPlayer et de la posBateau. 
							idPlayer = Integer.parseInt(json.getString("idPlayer"));
							int posBateau = Integer.parseInt(json.getString("posBateau"));
							room.getPlayerById(idPlayer).setPosBateau(posBateau);
							room.getPlayerById(idPlayer).setReady(true);
							
							lastAction = room.getPlayerById(idPlayer).getNom() + " a positionné son bateau !";
							
							// Changement du jeton. 
							if(room.getNbPlayer() == room.getNbPlayerMax()){
								// est-ce que tous le joueurs sont prêt ? 
								if(room.isStart())
									room.setJeton(room.getPlayer(0).getId()); // On donne le premier jeton ! 
							}		
							out.println("OK");
						break;
					
						case "shoot":
							// Lecture de l'idPlayer et de la posTir. 
							idPlayer = Integer.parseInt(json.getString("idPlayer"));
							int posTir = Integer.parseInt(json.getString("posTir"));
							
							if(room.getJeton() == idPlayer)  // On vérifie que c'est bien le joueur qui a le droit de parler.
							{
								// Préparation de la réponse: 
								// Liste des id des joueurs touchés
								ArrayList<Integer> lShoot = room.CheckShoot(idPlayer, posTir);
								lastAction = room.getPlayerById(idPlayer).getNom()+" a tiré en position "+posTir+" et a touché ";
								try {
									dataset.put("nbPlayerDead",lShoot.size());
									for(int i=0;i<lShoot.size();i++){
										dataset.put("playerDead"+i, lShoot.get(i));
										lastAction += room.getPlayerById(lShoot.get(i)).getNom()+" ";
									}	
								} catch (JSONException e) {System.out.println("Erreur JSON client:"+e.getMessage());}
								
								if(lShoot.size()<1)
									lastAction += "personne !";
								//System.out.println(dataset);
								out.println(dataset);
								
								// Changement du jeton
								room.setJeton(room.NextPlayer(idPlayer));		
							}
							else
								System.out.println("ERREUR: Ce n'est pas toi qui a le JETON !");
						break;
						
						case "exit":
							idPlayer = Integer.parseInt(json.getString("idPlayer"));
							System.out.println("Fermeture du serveur pour le port:"+lsock.get(s).getPort());
							out.println("Tchao");
							
							lsock.get(s).close();	
							
							room.getPlayerById(idPlayer).Kill();
							lastAction = room.getPlayerById(idPlayer).getNom()+" est mort en quittant la partie.";
							
							if(room.getJeton() == idPlayer)
							{
								room.setJeton(room.NextPlayer(idPlayer));
							}
							
							boolean SockLife = false;
							for(int i=0;i<lsock.size();i++){
								if(lsock.get(i).isClosed()==false)
									SockLife = true;
							}
							if(!SockLife){
								execute = false;
								out.close();
							}
							break;
							
						case "lastAction":
							dataset.put("lastAction",lastAction);
							out.println(dataset);
							break;
					}
				}
			}
			catch(Exception e){}	
		}
	
	}
}
