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

	private String ip; 
	private int port;
	private Socket sock; 
	private InputStreamReader isr; 
	private BufferedReader in;
	private Player player;
	private boolean busy = false; 
	
	public Client(String ip, int port){
		this.ip = ip; 
		this.port = port;
		player = null;
	}
	
	public void InitConnexionServer()
	{
		try {
			if(sock != null)
			{
				sock.close();
				in.close();	
			}	
			sock = new Socket(ip,port);
			isr = new InputStreamReader(sock.getInputStream());
			in = new BufferedReader(isr);
			in.ready();
		} catch (IOException e) {System.out.println("Erreur Client:"+e.getMessage());}
	}
	
	public Player ConnexionRoom(String namePlayer, int idRoom)
	{
		InitConnexionServer();
		player = null;
		try{
			JSONObject dataset = new JSONObject();
			dataset.put("methode","joinRoom");
			dataset.put("nomClient", namePlayer);
			dataset.put("idRoom",idRoom);
			// Afficher msg retour et récup idJoueur
			JSONObject json = new JSONObject(EnvoiRequete(dataset));
			String msgBack = (String) json.get("messageBack");
			System.out.println("Connexion ROOM n°"+idRoom+" - Réponse serveur: "+msgBack);
			int idPlayer = (int) json.get("idPlayer");
			player = new Player(idPlayer,namePlayer);
		} catch (Exception e) { System.out.println("Erreur client:"+e.getMessage());}

		return player;
	}
	
	
	public Player CreateRoom(String namePlayer, String nameRoom, int NbPlayerRoom)
	{	
		InitConnexionServer();
		player = null; 
		try{
			JSONObject dataset = new JSONObject();
			dataset.put("methode","createRoom");
			dataset.put("nameRoom",nameRoom);
			dataset.put("nameCreator", namePlayer);	
			dataset.put("nbPlayer",NbPlayerRoom);
			
			// Afficher msg retour et récup idJoueur
			JSONObject json = new JSONObject(EnvoiRequete(dataset));
			String msgBack = (String) json.get("messageBack");
			System.out.println("Creation ROOM "+nameRoom+" - Réponse serveur: "+msgBack);
			int idPlayer = (int) json.get("idPlayer");
			player = new Player(idPlayer,namePlayer);
		} catch (Exception e) { System.out.println("Erreur client:"+e.getMessage());}
				
		return player;
	}
	
	public ArrayList<Room> ListRoom()
	{
		ArrayList<Room> lRoom = new ArrayList<Room>();

		InitConnexionServer();
		try{
			JSONObject dataset = new JSONObject();
			dataset.put("methode","listRoom");
			
			// Afficher retour 
			JSONObject json = new JSONObject(EnvoiRequete(dataset));
			int nbRoom = json.getInt("nbRoom");
			for(int i=0;i<nbRoom;i++)
			{
				int idRoom = json.getInt("idRoom"+i);
				String nameRoom = json.getString("nameRoom"+i);
				String creator = json.getString("creator"+i);
				int nbPlayerMax = json.getInt("nbPlayerMax"+i);
				lRoom.add(new Room(idRoom, nameRoom, creator, nbPlayerMax));
			}
		} catch (Exception e) { System.out.println("Erreur client:"+e.getMessage());}

		return lRoom;
	}
	
	public Player getPlayer(){return player;}
	
	public Room InfoRoom()
	{
		Room room = null;

		JSONObject dataset = new JSONObject();
		try {
			dataset.put("methode", "infoRoom");
			String result = EnvoiRequete(dataset);
			JSONObject json = new JSONObject(result);
			//System.out.println("INFO ROOM JSON RECEPT: CONTENU:"+json.toString());
			int idRoom = json.getInt("idRoom");
			String nameRoom = json.getString("nameRoom");
			String creatorRoom = json.getString("creatorRoom");
			int nbPlayerMax = json.getInt("nbPlayerMax");
			int nbPlayer = json.getInt("nbPlayer");
			ArrayList<Player> lPlayer = new ArrayList<Player>();
			for(int i=0;i<nbPlayer;i++)
			{
				lPlayer.add(new Player(json.getInt("idPlayer"+i),json.getString("namePlayer"+i),json.getBoolean("lifePlayer"+i),json.getBoolean("readyPlayer"+i)));
			}
			boolean roomFull = json.getBoolean("roomFull");
			int jeton = json.getInt("jeton");
			
			room = new Room(idRoom, nameRoom, creatorRoom, nbPlayerMax, lPlayer, roomFull, jeton);
			
			System.out.println("JSON client: "+dataset);
			
		} catch (JSONException e) {System.out.println("Erreur JSON client INFO ROOM:"+e.getMessage());}
				
		return room;
	}
	
	public Socket getSock() {
		return sock;
	}

	public void SelectPosition(int posBateau)
	{			
		JSONObject dataset = new JSONObject();
		try {
			dataset.put("methode", "selectPosition");
			dataset.put("idPlayer",player.getId());
			dataset.put("posBateau", posBateau);
			EnvoiRequete(dataset);
			//System.out.println("JSON client: "+dataset);
		} catch (JSONException e) {System.out.println("Erreur JSON client SELEC POS:"+e.getMessage());}			
	}
	
	public ArrayList<Integer> Shoot(int posTir)
	{
		ArrayList<Integer> retour = new ArrayList<Integer>();
				
		JSONObject dataset = new JSONObject();			
		try {
			dataset.put("methode", "shoot");
			dataset.put("idPlayer",player.getId());
			dataset.put("posTir", posTir);
			
			//System.out.println("\nA envoyer JSON client: "+dataset);
			
			String result = EnvoiRequete(dataset);
			JSONObject json = new JSONObject(result);
			int nbPlayerDead = json.getInt("nbPlayerDead");
			for(int i=0;i<nbPlayerDead;i++)
			{
				retour.add(json.getInt("playerDead"+i));
			}
			
		} catch (JSONException e) {System.out.println("Erreur JSON client SHOOT:"+e.getMessage());}
				
		return (retour);	
	}
	
	public void Exit()
	{
		try {
			JSONObject dataset = new JSONObject();
			try {
				dataset.put("methode", "exit");
				dataset.put("idPlayer",player.getId());
			} catch (JSONException e) {System.out.println("Erreur JSON client EXIT:"+e.getMessage());}
			System.out.println("JSON client: "+dataset);	
			EnvoiRequete(dataset);
			sock.close();
			in.close();	
			System.out.println("Fermeture du client.");
		} catch (IOException e) {System.out.println("Erreur fermeture client : "+e.getMessage());}	
	}
	
	public String LastAction()
	{
		String retour = "";			
		JSONObject dataset = new JSONObject();	
		try {
			dataset.put("methode", "lastAction");
			String result = EnvoiRequete(dataset);
			JSONObject json = new JSONObject(result);
			retour = json.getString("lastAction");
		} catch (JSONException e) {System.out.println("Erreur JSON client LAST ACTION:"+e.getMessage());}
				
		return retour;
	}
	
	private String EnvoiRequete(JSONObject dataset)
	{
		boolean methodeExecute = false; 
		String rep_srv = "";
		while(!methodeExecute)
		{
			if(!busy)
			{
				busy = true; 
				
				try {
					PrintWriter out = new PrintWriter(sock.getOutputStream(),true);
					out.println(dataset); // Envoi au srv du fichier json
					
					// Réception de la réponse du serveur
					rep_srv = in.readLine(); 
				
					//System.out.println("\n Réponse serveur:"+rep_srv);
	
				} catch (Exception e) {System.out.println("Erreur client:"+e.getMessage());return null;}	
				
				busy = false; 
				methodeExecute = true; 
			}
		}
		return rep_srv;
	}
}
