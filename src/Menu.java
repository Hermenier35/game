import processing.core.*;

import java.net.Inet4Address;
import java.net.Socket;

public class Menu extends PApplet {
	private PImage webImg;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//PApplet.main("Menu");
		CreateGameServer server = new CreateGameServer();
		server.execute();
		try {
			Inet4Address address = (Inet4Address) Inet4Address.getByName("10.188.217.29");
			Client c = new Client(new Socket(address, 1234), "Pierre");
			c.lanceClient();

		}catch (Exception e){
			System.err.println("Erreur sérieuse : "+e);
			e.printStackTrace(); System.exit(1);
		}
	}
	
	public void settings() {
		size(1366,766);
	}
	
	public void setup() {
		webImg = loadImage("C:\\Users\\pedro\\Pictures\\istockphoto-1089169294-1024x1024.jpg");
	}
	
	public void draw() {
		image(webImg,0,0); 
	}
}
