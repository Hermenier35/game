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
        deplacement();
        setImagePosition(calculDegree());
        this.p.image(imagePosition, this.position.x + this.camera.x - 23,this.position.y + this.camera.y - 21);
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
            x+=48;
        }
    }

    public void setImagePosition(float degree){
        Float key = 0f;
        for(Float deg : imagePositions.keySet()){
            if(Math.abs(deg- degree) < 7)
                key = deg;
        }
        imagePosition = imagePositions.get(key);
        imagePosition.loadPixels();
    }

    @Override
    public JSONObject transform() {
        JSONObject data = new JSONObject();
        data.setString("type", "unity_focus");
        data.setInt("IdTeam", this.idTeam);
        data.setInt("IdType", this.idType);
        data.setFloat("focusX", focus.x);
        data.setFloat("focusY", focus.y);
        return data;
    }

    public float calculDegree(){
        PVector vector = new PVector( position.x, 0);
        PVector vect = new PVector(focus.x - position.x, focus.y - position.y);
        vector.normalize();
        vect.normalize();
        float degree = p.degrees(PVector.angleBetween(vect, vector));
        if(focus.y >= position.y)
            return 360 - degree;
        else
            return degree;
    }
}
