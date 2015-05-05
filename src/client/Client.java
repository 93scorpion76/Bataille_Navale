package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.JSONException;
import org.json.JSONObject;

import connectors.Player;

public class Client {

	private Socket sock; 
	private InputStreamReader isr; 
	private BufferedReader in;
	private Player player;
	
	public Client(String ip, int port, String nomJoueur){
		try {
			sock = new Socket(ip,port);
			isr = new InputStreamReader(sock.getInputStream());
			in = new BufferedReader(isr);
			in.ready();
			JSONObject dataset = new JSONObject();
			dataset.put("Nom", nomJoueur);
			
			System.out.println("Ouverture client");
			// Afficher msg server et récup idJoueur
			JSONObject json = new JSONObject(EnvoiRequete(dataset));
			String msg_welcome = (String) json.get("Message_Welcome");
			System.out.println("Réponse serveur:"+msg_welcome);
			int idJoueur = (int) json.get("IdPlayer");
			
			player = new Player(idJoueur,nomJoueur);
				
			} catch (Exception e) { System.out.println("Erreur client:"+e.getMessage());}
	}
	
	public int Shoot(int posTir)
	{
		JSONObject dataset = new JSONObject();
		try {
			dataset.put("Methode", "Shoot");
			dataset.put("idPlayer",player.getId());
			dataset.put("posTir", posTir);
		} catch (JSONException e) {System.out.println("Erreur JSON client:"+e.getMessage()); return -1;}
		System.out.println("JSON client: "+dataset);
		return (Integer.parseInt(EnvoiRequete(dataset)));	
	}
	
	public void Exit()
	{
		try {
			JSONObject dataset = new JSONObject();
			try {
				dataset.put("Methode", "Exit");
			} catch (JSONException e) {System.out.println("Erreur JSON client:"+e.getMessage());}
			System.out.println("JSON client: "+dataset);
			EnvoiRequete(dataset);
			sock.close();
			in.close();	
			System.out.println("Fermeture du client.");
		} catch (IOException e) {System.out.println("Erreur fermeture client:"+e.getMessage());}
	}
	
	
	private String EnvoiRequete(JSONObject dataset)
	{
		try {
			
			PrintWriter out = new PrintWriter(sock.getOutputStream(),true);
			out.println(dataset); // Envoi au srv du fichier json
				
			// Réception de la réponse du serveur
			String rep_srv = in.readLine(); 
			System.out.println("Réponse serveur:"+rep_srv);
			
			return rep_srv;
		} catch (Exception e) {System.out.println("Erreur client:"+e.getMessage());return null;}
	}
}
