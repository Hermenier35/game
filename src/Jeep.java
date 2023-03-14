import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.data.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Jeep extends MovibleEntity{
    Map<Float, PImage> imagePositions;
    Sprite sprite;
    public Jeep(int idTeams, int idType, int life, PVector position, int dmgAttack, float fireRate, float speedMovement, PApplet p, PImage map, EventManager event, PVector camera) {
        super(idTeams, idType, life, position, dmgAttack, fireRate, speedMovement, p, map, event, camera);
        this.imagePositions = new HashMap<>();
    }

    @Override
    public void draw() throws InterruptedException {
        //this.p.image(imagePosition, this.position.x, this.position.y);
        deplacement();
        this.p.image(imagePosition, this.position.x + this.camera.x,this.position.y + this.camera.y);
        //System.out.println("x : " + this.p.mouseX + "  y: " + this.p.mouseY);
        //System.out.println("degree : " + calculDegree());
    }

    @Override
    public void setup() {
        focus.x = position.x;
        focus.y = position.y;
        this.sprite = new Sprite(this.p);
        this.sprite.loadFromFile(Path.JEEP);
        int x = 0;
        for (float degree = 360; degree >= 0; degree-=11.612){
            imagePositions.put(degree, sprite.loadPosition(x,0,46,43));
            x+=46;
        }
    }

    public void setImagePosition(float degree){
        imagePosition = imagePositions.get(degree);
        imagePosition.loadPixels();
    }

    @Override
    public JSONObject transform() {
        return null;
    }

    public float calculDegree(){
       return this.position.angleBetween(position, focus);
    }
}
