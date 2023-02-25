
import controlP5.ControlP5;
import controlP5.DropdownList;
import processing.core.PApplet;

import java.net.Inet4Address;
import java.net.Socket;
import java.util.logging.Logger;

public class Main extends PApplet {
    Player p;
    boolean keypressed = false;
    ControlP5 cp5 ;
    private EventListener event;
    private static Socket socket;
    private static Client client;
    public Thread thread;
    DropdownList menu;

    public static void main(String[] args) {
        CreateGameServer server = new CreateGameServer();
        server.execute();
        try {
            Inet4Address address = (Inet4Address) Inet4Address.getByName("10.188.217.29");
            socket = new Socket(address, 1234);
            client = new Client(socket, "Pierre");
            client.lanceClient();
        }catch (Exception e){
            System.err.println("Erreur sérieuse : "+e);
            e.printStackTrace(); System.exit(1);
        }
        PApplet.main("Main");

    }
    public void settings() {
        this.size(520, 520);
    }
    public void setup(){
        p = new Player(this);
        event = new EventListener(p.position,socket, client.id, p);
        thread = new Thread(event);
        thread.start();
        p.events.addListener("position", event);
        cp5 = new ControlP5(this);
        cp5.addButton("GO")
                .setValue(0)
                .setPosition(82,0)
                .setSize(40,20);
        menu = cp5.addDropdownList("Menu")
                .setPosition(0, 0)
                .setSize(80,200);

        menu.setBackgroundColor(color(190));
        menu.setItemHeight(20);
        menu.setBarHeight(15);
        menu.addItem("Multi player",0);
        menu.addItem("Single player",1);

    }
    public void draw(){


        background(255);
        p.show();
        if(this.keypressed){
            if(keyCode == UP ){
                this.p.moveY(-1);
                Logger.getGlobal().info("moving up");
            }
            if(keyCode == DOWN){
                this.p.moveY(1);
                Logger.getGlobal().info("moving down");
            }
            if(keyCode == LEFT){
                this.p.moveX(-1);
                Logger.getGlobal().info("moving left");

            }
            if(keyCode == RIGHT){
                this.p.moveX(1);
                Logger.getGlobal().info("moving right");

            }


        }




    }
     public void keyPressed(){
        this.keypressed = true;
        Logger.getGlobal().info("keypressed");

    }
    public void keyReleased(){
        this.keypressed = false;
        Logger.getGlobal().info("keyReleased");
    }
}