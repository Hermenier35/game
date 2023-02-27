
import controlP5.ControlP5;
import controlP5.DropdownList;
import processing.core.PApplet;

import java.util.logging.Logger;

public class Main extends PApplet {
    Player p;
    boolean keypressed= false;
    ControlP5 cp5 ;
    DropdownList menu;

    public static void main(String[] args) {
        PApplet.main("Main");
    }
    public void settings() {
        this.size(520, 520);
    }
    public void setup(){
        p = new Player(this);
        cp5 = new ControlP5(this);
        cp5.addButton("GO")
                .setValue(0)
                .setPosition(82,0)
                .setSize(40,20);
        menu = cp5.addDropdownList("Menu")
                .setPosition(0, 0)
                .setSize(80,200);

        menu.setBackgroundColor(color(190));
        menu.setItemHeight(20);
        menu.setBarHeight(15);
        menu.addItem("Multi player",0);
        menu.addItem("Single player",1);



    }
    public void draw(){


        background(255);
        p.show();
        if(this.keypressed){
            if(keyCode == UP ){
                this.p.moveY(-1);
                Logger.getGlobal().info("moving up");
            }
            if(keyCode == DOWN){
                this.p.moveY(1);
                Logger.getGlobal().info("moving down");
            }
            if(keyCode == LEFT){
                this.p.moveX(-1);
                Logger.getGlobal().info("moving left");

            }
            if(keyCode == RIGHT){
                this.p.moveX(1);
                Logger.getGlobal().info("moving right");

            }


        }




    }
     public void keyPressed(){
        this.keypressed = true;
        Logger.getGlobal().info("keypressed");

    }
    public void keyReleased(){
        this.keypressed = false;
        Logger.getGlobal().info("keyReleased");
    }
}