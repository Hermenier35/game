import processing.core.PApplet;
import processing.core.PVector;


public class Player{
    PVector position ;
    PApplet scene;
    public EventManager events;

    float velocity = 10f;
    Player(PApplet scene){
        this.scene = scene;
        this.position = new PVector(scene.width/4,scene.height/4);
        events = new EventManager("position");
    }
    void moveX(int direction){
        this.position.x +=velocity*direction;
        events.notify("position", position);
    }
    void moveY(int direction){
        this.position.y +=velocity*direction;
        events.notify("position", position);
    }

    void show(){
        this.scene.fill(0);
        this.scene.stroke(0);
        this.scene.rect(this.position.x,this.position.y,20,20);
    }

}
