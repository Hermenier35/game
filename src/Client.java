import processing.data.JSONObject;

import java.io.*;
import java.net.Socket;

public class Client{
	public int id;
	public String name;
	public Socket socket;
	public boolean startGame;

	public Client(String name){
		this.name = name;
		this.startGame = false;
	}
	public void lanceClient() {
		try {
			envoiPseudo();
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			JSONObject data = new JSONObject(in);
			//System.out.println("Client :" +data);
			if(data.getString("type").equals("attribution_id"))
				this.id = data.getInt("id");
		} catch(Exception e) {
			System.err.println("Erreur sérieuse : "+e);
			e.printStackTrace(); System.exit(1);
		}
	}

	public void envoiPseudo(){
		try {
			JSONObject connectSalon = new JSONObject();
			System.out.println("envoi pseudo");
			connectSalon.setString("type", "connectSalon");
			connectSalon.setString("pseudo", this.name);
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			pw.println(connectSalon);
			pw.flush();
		}catch(Exception e) {
			System.err.println("Erreur sérieuse : "+e);
			e.printStackTrace(); System.exit(1);
		}
	}

	public void sendStartGame(){
		try {
			JSONObject startGame = new JSONObject();
			startGame.setString("type", "startGame");
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			pw.println(startGame);
			pw.flush();
		}catch (Exception e){
			System.err.println("Erreur send startGame : " + e);
			e.printStackTrace();
			System.exit(1);
		}
	}
}
