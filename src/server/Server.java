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
			Room room = new Room(nbRoom);
			nbRoom++;
			int nbclient = 0;
			ServerSocket srv = new ServerSocket(numPort);
			System.out.println("Srv à l'écoute...");
			while(true)
			{			
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
					room = new Room(nbRoom);
					nbRoom++;
					room.AddPlayer(new Player(nbclient, nom_client));
				}
				
				// Envoi d'un msg de bienvenu + de l'id du joueur associé à ce client.
				PrintWriter out = new PrintWriter(sock.getOutputStream(),true);
				JSONObject dataset = new JSONObject();
				String msg_welcome = "Bienvenu sur la room n°"+room.getIdRoom();
				dataset.put("Message_Welcome",msg_welcome);
				dataset.put("IdPlayer", nbclient);
				out.println(dataset); // Envoi au client le fichier json
	
				// Démarrer Thread runnable avec en paramètre la socket. 
				new Thread(new ServerThread(sock,room)).start();
				nbclient++;
			}
		} catch (Exception e) {System.out.println("Erreur serveur:"+e.getMessage());}
	}
}
