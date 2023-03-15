import controlP5.ControlP5;
import processing.core.*;
import processing.data.JSONObject;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Game implements Listener {
    public final int MAX_WIDTH = 4000, MAX_HEIGHT = 4000;
    List<Guest> guests;
    Client client;
    PApplet pApplet;
    Guest hote;
    ControlP5 cp5;
    List<JSONObject> datas;
    PImage map, or, herbe, rock;
    PVector camera;
    boolean mouseAction;
    int cornerX = 0;
    int cornerY = 0;
    PShape rectangle;
    Jeep jeep;
    Set<MovibleEntity> selectedUnity;
    Set<MovibleEntity> myUnity;
    Set<MovibleEntity> enemiUnity;

    public Game(List<Guest> guests, Client client, PApplet pApplet, Guest hote, ControlP5 cp5) {
        this.guests = guests;
        this.client = client;
        this.pApplet = pApplet;
        this.hote = hote;
        this.cp5 = cp5;
        this.datas = Collections.synchronizedList(new LinkedList<>());
        this.camera = new PVector(0,0);
        this.mouseAction = false;
        this.selectedUnity = new HashSet();
        this.myUnity = new HashSet<>();
        this.enemiUnity = new HashSet<>();
    }

    public void setup(){
        this.or = pApplet.loadImage("graphic/or.jpg");
        or.setLoaded();
        this.herbe = pApplet.loadImage("graphic/herbe.jpg");
        herbe.setLoaded();
        this.rock = pApplet.loadImage("graphic/rock.png");
        rock.setLoaded();
        map();
        pApplet.rectMode(pApplet.CORNERS);
        pApplet.fill(pApplet.color(15,176,245), 50);
        jeep = new Jeep(0,0,10,new PVector(200,20),20,10,0.1F,this.pApplet,map, new EventManager(), camera);
        jeep.setup();
        jeep.setImagePosition(348.388F);
        myUnity.add(jeep);
        //jeep.setFocus(new PVector(100, 200));
    }

    @Override
    public void update(String eventType, JSONObject data) throws InterruptedException {

    }

    public void draw() throws InterruptedException {
        camera();
        pApplet.image(map, camera.x, camera.y);
        drawSelect();
        actionMouse();
        jeep.draw();
    }

    public void camera(){
        if(pApplet.mouseX> pApplet.width-40 && camera.x > -3500) {
            camera.x -=pApplet.mouseX - pApplet.width + 40;
            pApplet.image(map, camera.x, camera.y);
        }

        if(pApplet.mouseX < 40 && camera.x < -20) {
            camera.x += 40-pApplet.mouseX;
            pApplet.image(map, camera.x, camera.y);
        }

        if(pApplet.mouseY > pApplet.height - 40 && camera.y > -3500){
            camera.y -= pApplet.mouseY - pApplet.height + 40;
            pApplet.image(map, camera.x, camera.y);
        }

        if(pApplet.mouseY < 40 && camera.y < -20){
            camera.y += 40 - pApplet.mouseY;
            pApplet.image(map, camera.x, camera.y);
        }
    }

    public void map(){
        map = pApplet.createImage(MAX_WIDTH, MAX_HEIGHT, pApplet.RGB);
        //this.pApplet.translate();
        map.loadPixels();
        Random r = new Random();
        for(int i = 0; i < MAX_HEIGHT; i+=20){
            for(int j = 0; j < MAX_WIDTH; j+=20){
                if(i > 0 && j > 0 && j < MAX_WIDTH -20 && i < MAX_HEIGHT-20) {
                    int random = r.nextInt(0, 10);
                    if(random<9)
                        map.set(i, j, herbe);
                    else
                        map.set(i, j, or);
                }
                else
                    map.set(i, j, rock);
            }
        }
    }

    public void drawSelect(){
        if(pApplet.mousePressed && pApplet.mouseButton==pApplet.LEFT && !mouseAction){
            cornerX = pApplet.mouseX;
            cornerY = pApplet.mouseY;
            mouseAction = true;
        }
        if(pApplet.mousePressed && mouseAction && pApplet.mouseButton == pApplet.LEFT){
            //pApplet.rect(cornerX, cornerY, pApplet.mouseX, pApplet.mouseY);
            rectangle = pApplet.createShape(PConstants.RECT,cornerX, cornerY, pApplet.mouseX, pApplet.mouseY);
            pApplet.shape(rectangle);
        }
        if(!pApplet.mousePressed && mouseAction){
            recoverSelectUnits(cornerX, cornerY, pApplet.mouseX, pApplet.pmouseY);
            mouseAction = false;
        }
    }

    public void recoverSelectUnits(int x, int y, int opositeX, int opositeY){
        for(MovibleEntity unity : myUnity){
            if(unity.position.x >= x && unity.position.x <= opositeX &&
               unity.position.y >= y && unity.position.y <= opositeY ){
                unity.setSelected(true);
                selectedUnity.add(unity);
            }
        }
    }

    public void actionMouse(){
        if(pApplet.mousePressed && pApplet.mouseButton == pApplet.RIGHT){
            if(selectedUnity.size()>0){
                for(MovibleEntity unity : selectedUnity){
                    unity.setFocus(new PVector(pApplet.mouseX-camera.x, pApplet.mouseY-camera.y));
                }
            }
        }

        if(pApplet.mousePressed && pApplet.mouseButton == pApplet.LEFT){
            selectedUnity.clear();
        }
    }
}
