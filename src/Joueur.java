import processing.data.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class Joueur implements Runnable {
	int id;
	JSONObject data;
	String pseudo;
	Socket socket;
	EventManager manager;
	
	public Joueur(int id, Socket socket) {
		this.id = id;
		this.socket = socket;
		manager = new EventManager("position");
	}

	@Override
	public void run() {
		try {
			this.handle();
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	
	public void handle() throws IOException {
		while(true) {
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			data = JSONObject.parse(in.readLine());
			manager.notify("data" ,data);
		}
	}
	
	
	
}
