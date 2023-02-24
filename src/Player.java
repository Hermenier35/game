import processing.core.PApplet;
import processing.core.PVector;

public class Player {
    PVector position ;
    PApplet scene;

    float velocity = 10f;
    Player(PApplet scene){
        this.scene = scene;
        this.position = new PVector(scene.width/2,scene.height/2);

    }
    void moveX(int direction){

        this.position.x +=velocity*direction;
    }
    void moveY(int direction){
        this.position.y +=velocity*direction;
    }

    void show(){
        this.scene.fill(0);
        this.scene.stroke(0);
        this.scene.rect(this.position.x,this.position.y,20,20);
    }
}
