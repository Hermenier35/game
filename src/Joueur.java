import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class Joueur implements Runnable {
	int x,y;
	int id;
	Socket socket;
	
	public Joueur(int x, int y, int id, Socket socket) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.socket = socket;
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
			System.out.println(in.readLine());
		}
	}
	
	
	
}
