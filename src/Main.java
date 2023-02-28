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
      /*  if (mouseX >150 && mouseX < 340 && mouseY > 200 && mouseY < 310)
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

        }*/
        System.out.println(controlEvent.isController());
        if(controlEvent.getController() instanceof Button){
            Button button = (Button) controlEvent.getController();
            if(button.isMouseOver()){
                if( button.getName().equals("Create Server")){
                    CreateGameServer server = new CreateGameServer();
                    server.execute();
                    connectClient("localhost", "admin");
                    System.out.println("create server on");
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
                      //  connectClient(ip.getText(), pseudo.getText());
                      //  ip.remove();
                      //  pseudo.remove();
                       clear = true;

                        /*for(Controller c : gui){
                           c.remove();
                        }*/


                        String[] processingArgs = {"Salon"};
                        // PApplet.runSketch(processingArgs, new Salon());


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
            eventEntrant = new EventListenerEntrant(socket);
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