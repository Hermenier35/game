import processing.core.PApplet;
import processing.data.JSONObject;

public class Salon extends PApplet implements Listener{
    PApplet f;



    public Salon(PApplet f) {
        this.f = f;
       // f.clear();
    }

    public void settings(){
       this.size(800, 600);
    }

    public void setup(){

    }

    public void draw(){
        System.out.println("test");
        f.background(0);
    }


    @Override
    public void update(String eventType, JSONObject data) {

    }
}
