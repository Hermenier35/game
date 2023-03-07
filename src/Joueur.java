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
	ReentrantLock reentrantLock;
	
	public Joueur(int id, Socket socket, ReentrantLock reentrantLock) {
		this.id = id;
		this.socket = socket;
		manager = new EventManager("data");
		this.reentrantLock = reentrantLock;
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
			reentrantLock.lock();
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				if(in.ready()) {
					data = new JSONObject(in);
					data.setInt("id", this.id);
					if (data.getString("type").equals("connectSalon")) {
						this.pseudo = data.getString("pseudo");
					}
					//System.out.println("joueur :" + data);
					manager.notify("data", data);
				}
			}catch(Exception e){
				throw new RuntimeException(e);
			}finally {
				reentrantLock.unlock();
			}
		}
	}
	
	
	
}
