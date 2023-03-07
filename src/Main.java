import controlP5.*;
import processing.core.PApplet;
import java.net.Inet4Address;
import java.net.Socket;

public class Main extends PApplet{

    private static final int
    //
    LAUNCHER=0, SALON=1, GAME=2;
    private int gameScreen = LAUNCHER;
    ControlP5 cp5 ;
    private EventListenerSortant eventSortant;
    private EventListenerEntrant eventEntrant;
    private Socket socket;
    private Client client;
    public Thread thread;
    public Thread threadEventEntrant;
    private boolean cleanLaunch = false;
    private Salon salon;

    public static void main(String[] args) {
        String[] processingArgs = {"Main"};
        Main main = new Main();
        PApplet.runSketch(processingArgs, main);
    }
    public void setup(){
        cp5 = new ControlP5(this);
        cp5.addButton("Create Server")
                .setValue(-1)
                .setPosition(this.width/2 - 100,this.height/2-15)
                .setSize(200,30);
        cp5.addTextfield("IP", this.width/2 - 100, this.height/2 + 25, 90, 30)
                .setText("localhost");
        cp5.addTextfield("PSEUDO", this.width/2 +10, this.height/2 + 25, 90, 30)
                .setText("Pseudo");
        cp5.addButton("Join")
                .setValue(-1)
                .setPosition(this.width/2 - 100, this.height/2 + 65)
                .setSize(50, 30);
    }

    public void settings(){
        this.size(520, 520);
    }
    public void draw(){

        // v1

        switch(gameScreen){
            case LAUNCHER: initScreen();break;
            case SALON:salonScreen();
                salon.draw();
                break;
            case GAME:gameScreen();break;
            default: exit();
        }

    }
    @Override
    public void mouseClicked() {
        super.mouseClicked();
        Button join = (Button) cp5.get("Join");
        Button create = (Button) cp5.get("Create Server");

        if(join.isMouseOver()){
            Textfield ip = (Textfield) cp5.get("IP");
            Textfield pseudo = (Textfield) cp5.get("PSEUDO");
            this.client = new Client(pseudo.getText());
            connectClient(ip.getText(), pseudo.getText(), client);
            salon = new Salon(new Guest(pseudo.getText()), client, cp5, this);
            eventEntrant.events.addListener("data", salon);
            System.out.println("deb");
            initSalon();
            System.out.println("mid");
            startSalon();
            System.out.println("fin");
        }

        if(create.isMouseOver()){
            CreateGameServer server = new CreateGameServer();
            server.execute();
            this.client = new Client("admin");
            connectClient("localhost", "admin", client);
            System.out.println("create server on");
            salon = new Salon(new Guest("admin"), client, cp5, this);
            eventEntrant.events.addListener("data", salon);
            System.out.println("deb");
            initSalon();
            System.out.println("mid");
            startSalon();
            System.out.println("fin");
        }

    }

    public void connectClient(String ip, String pseudo, Client client){
        try {
            //*******connection au serveur*******
            Inet4Address address = (Inet4Address) Inet4Address.getByName(ip);
            socket = new Socket(address, 1234);
            socket.setTcpNoDelay(true);
            client.socket = socket;
            //client.lanceClient();

            //***********************************

            //****** 2 threads pour gérer la data entrante et sortante
            eventSortant = new EventListenerSortant(socket);
            eventEntrant = new EventListenerEntrant(socket);
            threadEventEntrant = new Thread(eventEntrant);
            thread = new Thread(eventSortant);
        }catch (Exception e){
            System.err.println("Erreur sérieuse : "+e);
            e.printStackTrace(); System.exit(1);
        }
    }

    private void initScreen() {
        // codes of initial screen
        background(200);
        surface.setTitle("Laucher");
    }

    private void initSalon(){
        cp5.remove("Create Server");
        cp5.remove("Join");
        cp5.remove("IP");
        cp5.remove("PSEUDO");
        //cp5 = new ControlP5(this);
        //cp5.setVisible(false);
        System.out.println(Thread.currentThread().getName());
        salon.setup();
        client.lanceClient();
        threadEventEntrant.start();
        thread.start();
        salon.hote.id = client.id;
        System.out.println("test");
        //cp5.setVisible(true);
    }
    private void salonScreen() {
        // codes of salon screen
        surface.setTitle("Salon");
    }
    private void gameScreen() {
    }
    private void startSalon(){
        gameScreen=SALON;
    }
}