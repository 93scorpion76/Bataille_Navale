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
				PrintWriter out = new PrintWriter(sock.getOutputStream(),true);
				
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
				out.println("Bienvenu sur la room n°"+room.getIdRoom());
				// Démarrer Thread runnable avec en paramètre la socket. 
				new Thread(new ServerThread(sock,room)).start();
				nbclient++;
			}
		} catch (Exception e) {System.out.println("Erreur serveur:"+e.getMessage());}
	}
	
	public static void main(String arg[]){
		new Server(1234);
	}	
}
