import controlP5.*;
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
    int position = 80;
    public Salon(Guest hote, Client client, ControlP5 cp5, PApplet pApplet) {
        guests = new ArrayList<>();
        this.hote = hote;
        guests.add(hote);
        this.client = client;
        this.cp5 = cp5;
        this.pApplet = pApplet;
        this.datas = Collections.synchronizedList( new LinkedList<>());
    }

    public void settings(){;
    }

    public void setup(){
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
        System.out.println("setup " + Thread.currentThread().getName());
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
    }

    public Guest getGuest(int id){
        for(Guest g:guests){
            if(g.id==id)
                return g;
        }
        return null;
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
        System.out.println( "createRow : " + Thread.currentThread().getName());
        cp5.addTextarea(pseudo)
                .setText(pseudo)
                .setColor(0)
                .setFont(pApplet.createFont("Lucida Sans", 18))
                .setPosition(120,position);
       /* cp5.addListBox("couleur :" + pseudo)
                .setPosition(200, position+10)
                .addItems(tabCouleur)
                .setSize(80,50)
                .setOpen(false);
        System.out.println("position :" + position);*/
        this.position+=20;
    }
}
