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
	public Server(int numPort){
		try {
			int nbRoom = 0;
			Room room = new Room(nbRoom,2);
			nbRoom++;
			int nbclient = 1;
			ServerThread srvTh = new ServerThread(room);
			ServerSocket srv = new ServerSocket(numPort);
			ArrayList<ServerThread> lThread = new ArrayList<ServerThread>();
			lThread.add(srvTh);
			System.out.println("Srv à l'écoute...");
			while(true)
			{	
				for(int i=0;i<lThread.size();i++)
				{
					if(lThread.get(i).isExecute()){
						nbclient--;
						lThread.remove(i);
						System.out.println("Un client vient de se déconnecter !");
					}
				}
				
				Socket sock = srv.accept();
				System.out.println("Client n°"+nbclient+" connecté sur le port: "+sock.getPort()+".");
				
				// Lecture du nom du client
				InputStreamReader isr = new InputStreamReader(sock.getInputStream());
				BufferedReader in = new BufferedReader(isr);
				JSONObject json = new JSONObject(in.readLine());
				String nom_client = (String) json.get("Nom");
				
				// Ajout du joueur à la room.
				if(!room.AddPlayer(new Player(nbclient, nom_client)))
				{
					room = new Room(nbRoom,2);
					srvTh = new ServerThread(room);
					lThread.add(srvTh);
					nbRoom++;
					room.AddPlayer(new Player(nbclient, nom_client));
				}
				
				// Envoi d'un msg de bienvenu + de l'id du joueur associé à ce client.
				PrintWriter out = new PrintWriter(sock.getOutputStream(),true);
				JSONObject dataset = new JSONObject();
				String msg_welcome = "Bienvenu "+nom_client+" sur la room n°"+room.getIdRoom();
				dataset.put("Message_Welcome",msg_welcome);
				dataset.put("IdPlayer", nbclient);
				out.println(dataset); // Envoi au client le fichier json
				srvTh.AddSocket(sock);
				new Thread(srvTh).start();
				nbclient++;
			}
		} catch (Exception e) {System.out.println("Erreur serveur:"+e.getMessage());}
	}
}
