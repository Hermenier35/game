import processing.core.PApplet;
import processing.data.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Salon implements Listener{
    List<Guest> guests;
    PApplet scene;
    Client client;
    public Salon(Guest hote, PApplet scene, Client client) {
        guests = new ArrayList<>();
        guests.add(hote);
        this.scene = scene;
        this.client = client;
    }

    public void settings(){
    }

    public void setup(){
    }

    public void draw(){
    }


    @Override
    public void update(String eventType, JSONObject data) {
        if(data.getString("type").equals("player_position")){
            Guest g = contains(data.getInt("id"));
            if(g!=null) {
                g.player.position.x = data.getFloat("positionX");
                g.player.position.y = data.getFloat("positionY");
            }
        }
        if(data.getString("type").equals("connectSalon")){
            Player p = new Player(scene);
            Guest g = new Guest(data.getInt("id"), data.getString("pseudo"), p );
            guests.add(g);
            System.out.println("total joueur dans le salon :" + guests.size());
        }
    }

    public Guest contains(int id){
        for(Guest g:guests){
            if(g.id==id)
                return g;
        }
        return null;
    }
}
