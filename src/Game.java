import controlP5.ControlP5;
import processing.core.*;
import processing.data.JSONObject;

import java.awt.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Game implements Listener {
    List<Guest> guests;
    Client client;
    PApplet pApplet;
    Guest hote;
    ControlP5 cp5;
    List<JSONObject> datas;
    PImage image;
    PImage or;
    PImage herbe;
    PImage rock;
    PVector camera;
    boolean mouseAction;
    int cornerX = 0;
    int cornerY = 0;
    PShape rectangle;
    public final int MAX_WIDTH = 4000, MAX_HEIGHT = 4000;

    public Game(List<Guest> guests, Client client, PApplet pApplet, Guest hote, ControlP5 cp5) {
        this.guests = guests;
        this.client = client;
        this.pApplet = pApplet;
        this.hote = hote;
        this.cp5 = cp5;
        this.datas = Collections.synchronizedList(new LinkedList<>());
        this.camera = new PVector(0,0);
        this.mouseAction = false;
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
    }

    @Override
    public void update(String eventType, JSONObject data) throws InterruptedException {

    }

    public void draw(){
        camera();
        drawSelect();
    }

    public void camera(){
        if(pApplet.mouseX> pApplet.width-40 && camera.x > -3500) {
            System.out.println(camera.x);
            camera.x -=pApplet.mouseX - pApplet.width + 40;
            pApplet.image(image, camera.x, camera.y);

        }

        if(pApplet.mouseX < 40 && camera.x < -20) {
            System.out.println(camera.x);
            camera.x += 40-pApplet.mouseX;
            pApplet.image(image, camera.x, camera.y);
        }

        if(pApplet.mouseY > pApplet.height - 40 && camera.y > -3500){
            camera.y -= pApplet.mouseY - pApplet.height + 40;
            pApplet.image(image, camera.x, camera.y);
        }

        if(pApplet.mouseY < 40 && camera.y < -20){
            System.out.println(camera.y);
            camera.y += 40 - pApplet.mouseY;
            pApplet.image(image, camera.x, camera.y);
        }
    }

    public void map(){
        image = pApplet.createImage(MAX_WIDTH, MAX_HEIGHT, pApplet.RGB);
        image.loadPixels();
        Random r = new Random();
        for(int i = 0; i < MAX_HEIGHT; i+=20){
            for(int j = 0; j < MAX_WIDTH; j+=20){
                if(i > 0 && j > 0 && j < MAX_WIDTH -20 && i < MAX_HEIGHT-20) {
                    int random = r.nextInt(0, 10);
                    if(random<9)
                        image.set(i, j, herbe);
                    else
                        image.set(i, j, or);
                }
                else
                    image.set(i, j, rock);
            }
        }
    }

    public void drawSelect(){
        if(pApplet.mousePressed && !mouseAction){
            cornerX = pApplet.mouseX;
            cornerY = pApplet.mouseY;
            mouseAction = true;
        }
        if(pApplet.mousePressed && mouseAction){
            //pApplet.rect(cornerX, cornerY, pApplet.mouseX, pApplet.mouseY);
            rectangle = pApplet.createShape(PConstants.RECT,cornerX, cornerY, pApplet.mouseX, pApplet.mouseY);
        }
        if(!pApplet.mousePressed && mouseAction){
            pApplet.shape(rectangle);
            mouseAction = false;
        }
    }
}
