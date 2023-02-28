import processing.data.JSONObject;

import java.io.*;
import java.net.Socket;

public class Client{

	public static final int PORT = 1234;
	public int id;
	private String name;
	private Socket socket;
	private JSONObject data;

	public Client(Socket socket, String name){
		this.name = name;
		this.socket = socket;
	}
	public void lanceClient() {
		try {
			envoiPseudo();
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			data = new JSONObject(in);
			System.out.println(data);
		} catch(Exception e) {
			System.err.println("Erreur sérieuse : "+e);
			e.printStackTrace(); System.exit(1);
		}
	}

	public void envoiPseudo(){
		try {
			JSONObject connectSalon = new JSONObject();
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
}
