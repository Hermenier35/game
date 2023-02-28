import controlP5.*;
import processing.core.PApplet;

import java.net.Inet4Address;
import java.net.Socket;
import java.util.ArrayList;

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
    boolean clear = false;
    boolean start = false;
    private ArrayList<Controller> gui = new ArrayList<>();
    private Salon salon;

    public static void main(String[] args) {
       // PApplet.main("Main");
        String[] processingArgs = {"Main"};
        Main main = new Main();
        PApplet.runSketch(processingArgs, main);

    }
    public void settings() {
       // this.fullScreen();
       this.size(520, 520);
    }
    public void setup(){
        p = new Player(this);
       // size(200, 200);

        cp5 = new ControlP5(this);
        gui.add(cp5.addButton("Create Server")
                .setValue(-1)
                .setPosition(this.width/2 - 100,this.height/2-15)
                .setSize(200,30));
        gui.add(cp5.addButton("Join Server")
                .setValue(-1)
                .setPosition(this.width/2-100,this.height/2 + 25)
                .setSize(200, 30));
    }
    public void draw(){
        background(255);

        if(clear){
           for(Controller c: gui){
               c.remove();
           }

           start = true;

            clear = false;
        }
        if(start){
            p.show();
            if(this.keypressed){
                p.move(keyCode);
            }
            for(Guest g : salon.guests){
                if(g.id!= client.id)
                    g.player.show();
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
        if(controlEvent.getController() instanceof Button){
            Button button = (Button) controlEvent.getController();
            if(button.isMouseOver()){
                if( button.getName().equals("Create Server")){
                    CreateGameServer server = new CreateGameServer();
                    server.execute();
                    connectClient("localhost", "admin");
                    System.out.println("create server on");
                    salon = new Salon(new Guest(client.id, "admin", p), this, client);
                    eventEntrant.events.addListener("data", salon);
                    clear = true;
                }
                else if(button.getName().equals("Join Server")){
                    System.out.println("join server on ");
                    cp5.get("Join Server").remove();
                    gui.add(cp5.addTextfield("IP", this.width/2 - 100, this.height/2 + 25, 90, 30)
                            .setText("Address IP"));
                    gui.add(cp5.addTextfield("PSEUDO", this.width/2 +10, this.height/2 + 25, 90, 30)
                            .setText("Pseudo"));
                    gui.add(cp5.addButton("Join")
                            .setValue(-1)
                            .setPosition(this.width/2 - 100, this.height/2 + 65)
                            .setSize(50, 30));

                }
                else if( button.getName().equals("Join") ){
                    Button b =(Button)cp5.get("Join");
                    if(b.isMouseOver()) {
                        Textfield ip = (Textfield) cp5.get("IP");
                        Textfield pseudo = (Textfield) cp5.get("PSEUDO");
                        connectClient(ip.getText(), pseudo.getText());
                        salon = new Salon(new Guest(client.id, pseudo.getText(), new Player(this)), this, client);
                        eventEntrant.events.addListener("data", salon);
                        clear = true;
                    }


                }
            }




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
            eventEntrant = new EventListenerEntrant(socket, client.id);
            p.events.addListener("data", eventSortant);
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