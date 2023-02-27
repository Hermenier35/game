import g4p_controls.GButton;
import g4p_controls.GWinData;
import g4p_controls.GWindow;
import processing.core.PApplet;

import static processing.core.PConstants.JAVA2D;

public class InitGame {
    PApplet scene;
    GWindow window;
    GButton go;
    InitGame(PApplet scene){
        this.scene = scene;
        this.window = GWindow.getWindow(this.scene,"init",scene.width/2,scene.height/2,
                    scene.width/2,scene.height/2,JAVA2D );
        this.window.addDrawHandler(this.scene,"window");

        go = new GButton(this.window,0,0,40,20,"GO");

    }



}
