package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.json.JSONObject;

import connectors.Player;
import connectors.Room;
import server.Server;
import server.ServerThread;

public class Server {
	
	private int nbClient;
	private int nbRoom; 
	private ArrayList<ServerThread> lThread;
	private ServerSocket srv;

	public Server(int numPort){
		try {
			nbClient = 1;
			nbRoom = 1;
			srv = new ServerSocket(numPort);
			ArrayList<ServerThread> lThread = new ArrayList<ServerThread>();
			
			System.out.println("Serveur à l'écoute...");
			while(true)
			{		
				Socket sock = srv.accept();
				
				// Suppression des ROOM qui sont terminées. 
				for(int i=0;i<lThread.size();i++)
				{
					if(lThread.get(i).isExecute() == false){
						System.out.println("La room "+lThread.get(i).getRoom().getName()+" n°"+lThread.get(i).getRoom().getIdRoom()+" a été fermée !");
						lThread.remove(i);
					}
				}

				// Lecture de la méthode demandée par le client
				InputStreamReader isr = new InputStreamReader(sock.getInputStream());
				BufferedReader in = new BufferedReader(isr);
				JSONObject json = new JSONObject(in.readLine());
				String methode = json.getString("methode");
				
				PrintWriter out = new PrintWriter(sock.getOutputStream(),true);
				JSONObject dataset = new JSONObject();
				ServerThread srvTh;
				switch(methode)
				{
					case "joinRoom": // Rejoindre une room
						String nomClient = json.getString("nomClient");
						int idRoom = json.getInt("idRoom");
						
						String msgBack = "";
						int idPlayer = -1;
						// Récupération de la room
						for(int i=0;i<lThread.size();i++)
						{
							if(lThread.get(i).getRoom().getIdRoom() == idRoom)
							{
								// Ajout du joueur à la room.
								if(!lThread.get(i).getRoom().AddPlayer(new Player(nbClient,nomClient)))
								{
									// La room est complète ! 
									msgBack = "Erreur, la room est complète !";
									System.out.println(nomClient+" la room "+lThread.get(i).getRoom().getName() +" est complète ! Désolé.");
								}
								else
								{
									idPlayer = nbClient;
									nbClient++;
									msgBack = "Bienvenu "+nomClient+" sur la room "+lThread.get(i).getRoom().getName()+" n°"+lThread.get(i).getRoom().getIdRoom();
									System.out.println(nomClient+" est le client n°"+idPlayer+" connecté sur le port: "+sock.getPort()+". Dans la room n°"+lThread.get(i).getRoom().getIdRoom());
									lThread.get(i).AddSocket(sock);
									new Thread(lThread.get(i)).start();
								}
								i = lThread.size(); // Sortie de boucle. 
							}
						}
						
						// Envoi du msg de retour + l'id du joueur associé à ce client. -1 si pas pu rejoindre la room.		
						dataset.put("messageBack",msgBack);
						dataset.put("idPlayer", idPlayer);
						out.println(dataset); // Envoi au client le fichier json
						break;
				
					case "createRoom": // Creer une room
						String nameCreator = json.getString("nameCreator");
						String nameRoom = json.getString("nameRoom");
						int nbPlayer = json.getInt("nbPlayer");
						Room room = new Room(nbRoom,nameRoom,nameCreator,nbPlayer);
						nbRoom++;
						// Création de la room
						srvTh = new ServerThread(room);
						lThread.add(srvTh);
						System.out.println("La room "+nameRoom+" n°"+room.getIdRoom()+" a été créée !");
						
						// Le créateur rejoins automatiquement cette room ! 
						idPlayer = nbClient;
						nbClient++;
						System.out.println(nameCreator+" est le client n°"+idPlayer+" connecté sur le port: "+sock.getPort()+". Dans la room n°"+room.getIdRoom());

						room.AddPlayer(new Player(idPlayer, nameCreator));

						msgBack = "Bienvenu "+nameCreator+" sur votre room "+room.getName()+" n°"+room.getIdRoom();
						
						// Envoi du msg retour + l'id du joueur associé à ce client.	
						dataset.put("messageBack",msgBack);
						dataset.put("idPlayer", idPlayer);
						out.println(dataset); // Envoi au client le fichier json
						
						srvTh.AddSocket(sock);
						new Thread(srvTh).start();
						break;
										
					case "listRoom": // Liste les rooms où il y a encore de la place.
						dataset.put("nbRoom",lThread.size());
						for(int i=0;i<lThread.size();i++)
						{
							if(!lThread.get(i).getRoom().isFull())
							{
								dataset.put("idRoom"+i,lThread.get(i).getRoom().getIdRoom());
								dataset.put("nameRoom"+i,lThread.get(i).getClass().getName());
								dataset.put("creator"+i,lThread.get(i).getRoom().getCreator());
								dataset.put("nbPlayerMax"+i,lThread.get(i).getRoom().getNbPlayerMax());
							}	
						}
						out.println(dataset); // Envoi au client le fichier json
						break;
				}
			}
		} catch (Exception e) {System.out.println("Erreur serveur:"+e.getMessage());}
	}
}
