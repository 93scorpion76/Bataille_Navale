package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import connectors.Room;

public class ServerThread implements Runnable {

	private Socket sock;
	private Room room;
	
	public ServerThread(Socket socket, Room room) {
		this.sock = socket;
		this.room = room;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		boolean execute = true;
		
		while(execute)
		{
			try{		
				// Lecture de la méthode demandée par le client:
				InputStreamReader isr = new InputStreamReader(sock.getInputStream());
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
				}
			}
			catch(Exception e){}	
		}
	}
}
