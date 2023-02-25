
import controlP5.ControlEvent;
import controlP5.ControlListener;
import controlP5.ControlP5;
import controlP5.DropdownList;
import processing.core.PApplet;

import java.net.Inet4Address;
import java.net.Socket;

public class Main extends PApplet implements ControlListener {
    Player p;
    boolean keypressed = false;
    ControlP5 cp5 ;
    private EventListenerSortant eventSortant;
    private EventListenerEntrant eventEntrant;
    private static Socket socket;
    private static Client client;
    public Thread thread;
    public Thread threadEventEntrant;
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
        eventSortant = new EventListenerSortant(p.position,socket, client.id);
        eventEntrant = new EventListenerEntrant(socket);
        threadEventEntrant = new Thread(eventEntrant);
        threadEventEntrant.start();
        thread = new Thread(eventSortant);
        thread.start();
        p.events.addListener("position", eventSortant);


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
        menu.addItem("Create Server",0);
        menu.addItem("Join Server",1);
    }
    public void draw(){

        background(255);
        p.show();
        if(this.keypressed){
            if(keyCode == UP ){
                this.p.moveY(-1);
              //  Logger.getGlobal().info("moving up");
            }
            if(keyCode == DOWN){
                this.p.moveY(1);
               // Logger.getGlobal().info("moving down");
            }
            if(keyCode == LEFT){
                this.p.moveX(-1);
               // Logger.getGlobal().info("moving left");

            }
            if(keyCode == RIGHT){
                this.p.moveX(1);
               // Logger.getGlobal().info("moving right");

            }


        }

    }
     public void keyPressed(){
        this.keypressed = true;
       // Logger.getGlobal().info("keypressed");

    }
    public void keyReleased(){
        this.keypressed = false;
       // Logger.getGlobal().info("keyReleased");
    }

    @Override
    public void controlEvent(ControlEvent controlEvent) {
        System.out.println(controlEvent.getController().getValue());
    }
}