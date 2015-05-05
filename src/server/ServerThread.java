package server;

import java.net.Socket;

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
		
	}
	
	
	
}
