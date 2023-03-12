import processing.core.PApplet;
import processing.core.PImage;

public class Sprite {
    PApplet p;
    PImage image;

    public Sprite(PApplet p) {
        this.p = p;
    }

    public void loadFromFile(String path){
        image = p.loadImage(path);
        image.loadPixels();
    }

    public PImage loadPosition(int x, int y, int w, int h){
        return image.get(x,y,w,h);
    }
}
