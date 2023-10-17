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
    float cornerX = 0;
    float cornerY = 0;
    PShape rectangle;
    Jeep jeep;
    Set<MovibleEntity> selectedUnity;
    Set<MovibleEntity> myUnity;
    Set<MovibleEntity> enemiUnity;
    EventListenerSortant eventsSortant;

    public Game(List<Guest> guests, Client client, PApplet pApplet, Guest hote, ControlP5 cp5, EventListenerSortant eventsSortant) {
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
        this.eventsSortant = eventsSortant;
    }

    public void setup(){
        EventManager manager = new EventManager("data");
        this.or = pApplet.loadImage("graphic/or.jpg");
        or.setLoaded();
        this.herbe = pApplet.loadImage("graphic/herbe.jpg");
        herbe.setLoaded();
        this.rock = pApplet.loadImage("graphic/rock.png");
        rock.setLoaded();
        map();
        pApplet.rectMode(pApplet.CORNERS);
        pApplet.fill(pApplet.color(15,176,245), 50);
        jeep = new Jeep(0,0,10,new PVector(200,100),20,10,0.1F,this.pApplet,map, manager, camera);
        jeep.setup();
        testMovibleEntity(0, jeep);
        jeep.event.addListener("data", eventsSortant);
        //jeep.setFocus(new PVector(100, 200));
    }

    @Override
    public void update(String eventType, JSONObject data) throws InterruptedException {
        if(data.getString("type").equals("unity_focus")){
            int idTeam = data.getInt("IdTeam");
            int idType = data.getInt("IdType");
            float focusX = data.getFloat("focusX");
            float focusY = data.getFloat("focusY");
            updateUnity(idTeam, idType, focusX, focusY);
        }
    }

    public void draw() throws InterruptedException {
        camera();
        pApplet.image(map, camera.x, camera.y);
        drawSelect();
        actionMouse();
        drawAllUnity(myUnity);
        drawAllUnity(enemiUnity);
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
            rectangle = pApplet.createShape(PConstants.RECT,cornerX, cornerY, pApplet.mouseX, pApplet.mouseY);
            pApplet.shape(rectangle);
        }
        if(!pApplet.mousePressed && mouseAction){
            recoverSelectUnits(cornerX - camera.x, cornerY - camera.y, pApplet.mouseX - camera.x, pApplet.pmouseY - camera.y);
            mouseAction = false;
        }
    }

    public void recoverSelectUnits(float x, float y, float opositeX, float opositeY){
        for(MovibleEntity unity : myUnity){
            if(unity.position.x >= x && unity.position.x <= opositeX &&
               unity.position.y >= y && unity.position.y <= opositeY ){
                unity.setSelected(true);
                selectedUnity.add(unity);
            }
        }
    }

    public void actionMouse(){
        if(pApplet.mousePressed && pApplet.mouseButton == pApplet.RIGHT && !mouseAction){
            mouseAction = true;
            if(selectedUnity.size()>0){
                for(MovibleEntity unity : selectedUnity){
                    unity.setFocus(new PVector(pApplet.mouseX-camera.x, pApplet.mouseY-camera.y));
                }
            }
        }
        if(!pApplet.mousePressed)
            mouseAction = false;

        if(pApplet.mousePressed && pApplet.mouseButton == pApplet.LEFT && !mouseAction)
            selectedUnity.clear();
    }

    public void updateUnity(int idTeam, int idType, float focusX, float focusY){
        if(idTeam!=this.hote.id){
            boolean isUpdate = false;
            Iterator<MovibleEntity> ite = enemiUnity.iterator();
            while(ite.hasNext() && !isUpdate){
                MovibleEntity entity = ite.next();
                if(entity.idTeam == idTeam && entity.idType == idType){
                    entity.focus.x = focusX;
                    entity.focus.y = focusY;
                    isUpdate = true;
                }
            }
        }
    }

    public void testMovibleEntity(int idTeam, MovibleEntity entity){
        if(this.hote.id == idTeam)
            myUnity.add(entity);
        else
            enemiUnity.add(entity);
    }

    public void drawAllUnity(Set<MovibleEntity> entities) throws InterruptedException {
        Iterator<MovibleEntity> ite = entities.iterator();
        while(ite.hasNext())
            ite.next().draw();
    }
}
