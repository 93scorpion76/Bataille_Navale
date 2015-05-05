package test;

import client.Client;

public class TestClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Client c1 = new Client("localhost",1234,"Romain");
		Client c2 = new Client("localhost",1234,"Valentin");
		Client c3 = new Client("localhost",1234,"Bastien");
		Client c4 = new Client("localhost",1234,"Dorian");
		Client c5 = new Client("localhost",1234,"Clement");
		Client c6 = new Client("localhost",1234,"Godart");
	}

}
