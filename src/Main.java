
import controlP5.*;
import processing.core.PApplet;

import java.awt.event.MouseListener;
import java.net.Inet4Address;
import java.net.Socket;

public class Main extends PApplet implements ControlListener {
    Player p;
    boolean keypressed = false;
    ControlP5 cp5 ;
    private EventListenerSortant eventSortant;
    private EventListenerEntrant eventEntrant;
    private Socket socket;
    private Client client;
    public Thread thread;
    public Thread threadEventEntrant;
    boolean arret = false;

    public static void main(String[] args) {
       // PApplet.main("Main");
        String[] processingArgs = {"Main"};
        Main main = new Main();
        PApplet.runSketch(processingArgs, main);

    }
    public void settings() {
        this.size(520, 520);
    }
    public void setup(){
        p = new Player(this);
       // size(200, 200);
        /*

        p.events.addListener("position", eventSortant);  */


        cp5 = new ControlP5(this);
        cp5.addButton("Create Server")
                .setValue(0)
                .setPosition(150,200)
                .setSize(200,30);
        cp5.addButton("Join Server")
                .setValue(1)
                .setPosition(150,240)
                .setSize(200, 30);
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

        if (arret) {
            new Salon(this);
         //   noLoop();
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
        System.out.println(mouseX + " " + mouseY);
        if (mouseX >150 && mouseX < 340 && mouseY > 200 && mouseY < 310)
        switch (controlEvent.getName()) {
            case "Create Server" : CreateGameServer server = new CreateGameServer();
                                   server.execute();
                                   connectClient("localhost", "admin");break;
            case "Join Server" : cp5.get("Join Server").remove();
                                 cp5.addTextfield("IP", 150, 240, 90, 30)
                                         .setText("Address IP");
                                 cp5.addTextfield("PSEUDO", 250, 240, 90, 30)
                                                 .setText("Pseudo");
                                 cp5.addButton("Join")
                                         .setValue(3)
                                         .setPosition(150, 280)
                                         .setSize(50, 30);
                                 break;
            case "Join" : Button b =(Button)cp5.get("Join");
                          if(b.isMouseOver()) {
                              Textfield ip = (Textfield) cp5.get("IP");
                              Textfield pseudo = (Textfield) cp5.get("PSEUDO");
                              connectClient(ip.getText(), pseudo.getText());
                              String[] processingArgs = {"Salon"};
                             // PApplet.runSketch(processingArgs, new Salon());

                              arret = true;
                          }
                          break;

        }
    }

    public void connectClient(String ip, String pseudo){
        try {
            //*******connection au serveur*******
            Inet4Address address = (Inet4Address) Inet4Address.getByName(ip);
            socket = new Socket(address, 1234);
            client = new Client(socket, pseudo);
            client.lanceClient();
            //***********************************

            //****** 2 threads pour gérer la data entrante et sortante
            eventSortant = new EventListenerSortant(socket, client.id);
            eventEntrant = new EventListenerEntrant(socket);
            threadEventEntrant = new Thread(eventEntrant);
            thread = new Thread(eventSortant);
            threadEventEntrant.start();
            thread.start();
        }catch (Exception e){
            System.err.println("Erreur sérieuse : "+e);
            e.printStackTrace(); System.exit(1);
        }
    }
}