package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {

	private Socket sock; 
	private InputStreamReader isr; 
	private BufferedReader in;
	
	public Client(String ip, int port){
		try {
			sock = new Socket(ip,port);
			isr = new InputStreamReader(sock.getInputStream());
			in = new BufferedReader(isr);
			in.ready();
			System.out.println("Ouverture client");
			System.out.println("Réponse serveur:"+in.readLine());
		} catch (Exception e) { System.out.println("Erreur client:"+e.getMessage());} 
	}
	
	
	
	
}
