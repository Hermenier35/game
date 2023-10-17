import processing.data.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;


public class Joueur implements Runnable {
	int id;
	JSONObject data;
	String pseudo;
	Socket socket;
	EventManager manager;
	
	public Joueur(int id, Socket socket) {
		this.id = id;
		this.socket = socket;
		manager = new EventManager("data");
	}

	@Override
	public void run() {
		try {
			this.handle();
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
		
	}

	private boolean concatData(BufferedReader in){
		try {
			String rep = in.readLine();
			while(!rep.endsWith("}"))
				rep+=in.readLine();
			data = JSONObject.parse(rep);
			System.out.println("that rep :"+ rep);
			return true;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public void handle() throws IOException {
		while(true) {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				if(concatData(in)){
					data.setInt("id", this.id);
					if (data.getString("type").equals("connectSalon")) {
						this.pseudo = data.getString("pseudo");
					}
					//System.out.println("joueur :" + data);
					manager.notify("data", data);

				}
			}catch(Exception e){
				throw new RuntimeException(e);
			}
		}
	}
	
	
	
}
