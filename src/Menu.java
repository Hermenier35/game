import processing.core.*;
public class Menu extends PApplet {
	private PImage webImg;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//PApplet.main("Menu");
		CreateGameServer server = new CreateGameServer();
		server.execute();
		Client c = new Client();
		Client c2 = new Client();
		Client c3= new Client();
		Client c4 = new Client();
		Client c5 = new Client();
		c.lanceClient();
		c2.lanceClient();
		c3.lanceClient();
		c4.lanceClient();
		c5.lanceClient();
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
