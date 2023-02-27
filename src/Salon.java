import processing.core.PApplet;
import processing.core.PGraphics;

public class Salon extends PApplet{
    PApplet f;



    public Salon(PApplet f) {
        this.f = f;
       // f.clear();
    }

    public void settings(){
       // this.size(800, 600);
       // f.clear();
    }

    public void setup(){

    }

    public void draw(){
        System.out.println("test");
        f.background(0);
    }



}
