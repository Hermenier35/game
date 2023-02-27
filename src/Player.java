import processing.core.PApplet;
import processing.core.PVector;
import processing.data.JSONObject;


public class Player implements  ConvertJson{
    PVector position ;
    PApplet scene;
    public EventManager events;

    float velocity = 10f;
    Player(PApplet scene){
        this.scene = scene;
        this.position = new PVector(scene.width/4,scene.height/4);
        events = new EventManager("data");
    }
    void moveX(int direction){
        this.position.x +=velocity*direction;
        events.notify("data", transform());
    }
    void moveY(int direction){
        this.position.y +=velocity*direction;
        events.notify("data", transform());
    }

    void show(){
        this.scene.fill(0);
        this.scene.stroke(0);
        this.scene.rect(this.position.x,this.position.y,20,20);
    }

    @Override
    public JSONObject transform() {
        JSONObject data = new JSONObject();
        data.setString("type", "player_position");
        data.setFloat("positionX", position.x);
        data.setFloat("positionY", position.y);
        return data;
    }
}
