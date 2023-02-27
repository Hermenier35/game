import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class Joueur implements Runnable {
	double x,y;
	int id;
	Socket socket;
	EventManager manager;
	
	public Joueur(int x, int y, int id, Socket socket) {
		this.x = x;
		this.y = y;
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
			String paquet = in.readLine();
			//System.out.println(paquet);
			String []values = paquet.split(" ");
			switch(values[0]){
				case "position" : this.x = Double.parseDouble(values[2]);
						          this.y = Double.parseDouble(values[3]);
								  manager.notify("position", values);
					break;
				default: System.out.println(values[0]);
			}
		}
	}
	
	
	
}
