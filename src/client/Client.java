package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import connectors.Player;
import connectors.Room;

public class Client{

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
	
	public Player getPlayer(){return player;}
	
	public Room InfoRoom(){
		Room room = null;
		JSONObject dataset = new JSONObject();
		try {
			dataset.put("Methode", "InfoRoom");
			String result = EnvoiRequete(dataset);
			JSONObject json = new JSONObject(result);
			int idRoom = json.getInt("idRoom");
			int nbPlayerLife = json.getInt("nbPlayerLife");
			int nbPlayerMax = json.getInt("nbPlayerMax");
			int nbPlayer = json.getInt("nbPlayer");
			ArrayList<Player> lPlayer = new ArrayList<Player>();
			for(int i=0;i<nbPlayer;i++)
			{
				lPlayer.add(new Player(json.getInt("idPlayer"+i),json.getString("nomPlayer"+i),json.getBoolean("lifePlayer"+i),json.getBoolean("readyPlayer"+i)));
			}
			boolean roomFull = json.getBoolean("roomFull");
			int jeton = json.getInt("jeton");
			
			room = new Room(idRoom, nbPlayerLife, nbPlayerMax, lPlayer, roomFull, jeton);
			
			//System.out.println("JSON client: "+dataset);
			
		} catch (JSONException e) {System.out.println("Erreur JSON client:"+e.getMessage());}
		
		return room;
	}
	
	public Socket getSock() {
		return sock;
	}

	public void SelectPosition(int posBateau)
	{
		JSONObject dataset = new JSONObject();
		try {
			dataset.put("Methode", "SelectPosition");
			dataset.put("idPlayer",player.getId());
			dataset.put("posBateau", posBateau);
			EnvoiRequete(dataset);
			//System.out.println("JSON client: "+dataset);
		} catch (JSONException e) {System.out.println("Erreur JSON client:"+e.getMessage());}
	}
	
	public ArrayList<Integer> Shoot(int posTir)
	{
		JSONObject dataset = new JSONObject();
		ArrayList<Integer> retour = new ArrayList<Integer>();
		try {
			dataset.put("Methode", "Shoot");
			dataset.put("idPlayer",player.getId());
			dataset.put("posTir", posTir);
			
			//System.out.println("\nA envoyer JSON client: "+dataset);
			
			String result = EnvoiRequete(dataset);
			JSONObject json = new JSONObject(result);
			int nbPlayerDead = json.getInt("NbPlayerDead");
			for(int i=0;i<nbPlayerDead;i++)
			{
				retour.add(json.getInt("PlayerDead"+i));
			}
			
			
		} catch (JSONException e) {System.out.println("Erreur JSON client:"+e.getMessage());}
		return (retour);	
	}
	
	public void Exit()
	{
		try {
			JSONObject dataset = new JSONObject();
			try {
				dataset.put("Methode", "Exit");
				dataset.put("idPlayer",player.getId());
			} catch (JSONException e) {System.out.println("Erreur JSON client:"+e.getMessage());}
			System.out.println("JSON client: "+dataset);	
			EnvoiRequete(dataset);
			sock.close();
			in.close();	
			System.out.println("Fermeture du client.");
		} catch (IOException e) {System.out.println("Erreur fermeture client : "+e.getMessage());}
	}
	
	
	private String EnvoiRequete(JSONObject dataset)
	{
		try {
			PrintWriter out = new PrintWriter(sock.getOutputStream(),true);
			out.println(dataset); // Envoi au srv du fichier json
			
			// Réception de la réponse du serveur
			String rep_srv = in.readLine(); 

			//System.out.println("\n Réponse serveur:"+rep_srv);
			return rep_srv;
		} catch (Exception e) {System.out.println("Erreur client:"+e.getMessage());return null;}
	}
}
