
import controlP5.ControlP5;
import controlP5.Textarea;
import processing.core.PApplet;
import processing.data.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Salon implements Listener{
    private static final int
            BLEU=0, ROUGE=1, VERT=2, ORANGE = 3;
    private final String tabCouleur[] = {"Bleu", "Rouge", "Vert", "Orange"};
    List<Guest> guests;
    Client client;
    PApplet pApplet;
    Guest hote;
    ControlP5 cp5;
    List<JSONObject> datas;
    List<Textarea> drawGuests;
    int position = 80;
    public Salon(Guest hote, Client client, ControlP5 cp5, PApplet pApplet) {
        guests = new ArrayList<>();
        this.hote = hote;
        this.client = client;
        this.cp5 = cp5;
        this.pApplet = pApplet;
        this.datas = Collections.synchronizedList( new LinkedList<>());
        this.drawGuests = new ArrayList<>();
    }

    public void setup(){
        guests.add(hote);
        pApplet.background(0);
        pApplet.textSize(18);
        pApplet.textAlign(pApplet.RIGHT);
        pApplet.text("GUEST", 170, 50);
        pApplet.textAlign(pApplet.CENTER);
        pApplet.text("TEAM", 250, 50);
        pApplet.textAlign(pApplet.LEFT);
        pApplet.text("RACE", 320, 50);
        pApplet.rect(100, 70, 300, 400, 30);
        createRowsGuest(hote.pseudo);
        if(hote.pseudo.equals("admin")){
            cp5.addButton("startGame")
                    .setPosition(this.pApplet.width/2 - 25, 480)
                    .setSize(50, 30)
                    .setColorBackground(pApplet.color(0, 204, 0));
        }
    }

    public void draw(){
        if(!datas.isEmpty()) {
            createRowsGuest(datas.get(0).getString("pseudo"));
            datas.remove(0);
        }
    }


    @Override
    public void update(String eventType, JSONObject data) throws InterruptedException {
        //System.out.println(data);
        if(data.getString("type").equals("connectSalon") && !containsGuest(data.getInt("id"))){
            Guest g = new Guest(data.getString("pseudo"));
            g.id = data.getInt("id");
            //System.out.println(Thread.currentThread().getName());
            guests.add(g);
            datas.add(data);
            //System.out.println("salon :" + data);
            System.out.println("total joueur dans le salon :" + guests.size());
            client.envoiPseudo();
        }

        if(data.getString("type").equals("startGame")){
            client.startGame = true;
        }
    }

    public boolean containsGuest(int id){
        for(Guest g:guests){
            if(g.id==id) {
                System.out.println("id: " + id + "  =  " + g.pseudo);
                return true;
            }
        }
        System.out.println("ajout de : " + id);
        return false;
    }
    private synchronized void createRowsGuest(String pseudo){
        Textarea guest = new Textarea(cp5, pseudo);
        guest.setColor(0);
        guest.setText(pseudo);
        guest.setFont(pApplet.createFont("Lucida Sans", 18));
        guest.setPosition(120, position);
        drawGuests.add(guest);
        /*cp5.addTextarea(pseudo)
                .setText(pseudo)
                .setColor(0)
                .setFont(pApplet.createFont("Lucida Sans", 18))
                .setPosition(120,position);*/

        this.position+=20;
    }

    public void cleanSalon(){
        for(Textarea g : drawGuests){
            g.remove();
        }
    }
}
