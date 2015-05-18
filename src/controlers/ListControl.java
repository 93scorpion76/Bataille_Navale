package controlers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import view.ListRoomView;
import client.Client;

public class ListControl {

	private Client cli;
	private ListRoomView roomView;
	private String namePlayer;
	
	public ListControl(String name)
	{
		String emplacement = "";
		try{
			InputStream ips=new FileInputStream("config/config.txt"); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			while ((ligne=br.readLine())!=null){
				emplacement = ligne;
			}
			br.close(); 
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
		
		cli = new Client(emplacement, 1234);

		namePlayer = name;
		roomView = new ListRoomView(namePlayer);
		
		ListRoomControl tc = new ListRoomControl(namePlayer, roomView, cli, this);
		new Thread(tc).start();
	}
}
