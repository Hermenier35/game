import processing.core.PVector;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class Client {

	public static final int PORT = 1234;
	public int id;
	private String name;
	private Socket socket;

	public Client(Socket socket, String name){
		this.name = name;
		this.socket = socket;
	}
	public void lanceClient() {
		try {
			PrintWriter pw = new PrintWriter(
					new OutputStreamWriter(socket.getOutputStream()));
			pw.println(name);
			pw.flush();
			BufferedReader bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String message = bf.readLine();
			id = Integer.parseInt(message);
		} catch(Exception e) {
			System.err.println("Erreur sérieuse : "+e);
			e.printStackTrace(); System.exit(1);
		}
	}

}
