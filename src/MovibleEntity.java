import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public abstract class MovibleEntity implements ConvertJson{
    protected int idTeams;
    protected int idType;
    protected int life;
    protected PVector position , focus;
    protected int dmgAttack;
    protected float fireRate;
    protected float speedMovement;
    protected PApplet p;
    protected PImage map;
    protected EventManager event;
    protected  float velocity = 10f;
    protected PImage imagePosition;
    protected PVector camera;
    protected boolean isSelected;

    public MovibleEntity(int idTeams, int idType, int life, PVector position, int dmgAttack, float fireRate, float speedMovement, PApplet p, PImage map, EventManager event, PVector camera) {
        this.idTeams = idTeams;
        this.idType = idType;
        this.life = life;
        this.position = position;
        this.dmgAttack = dmgAttack;
        this.fireRate = fireRate;
        this.speedMovement = speedMovement;
        this.p = p;
        this.map = map;
        this.event = event;
        this.focus = new PVector();
        this.camera = camera;
        this.isSelected = false;
    }

    public abstract void draw() throws InterruptedException;
    public abstract void setup();
    public void deplacement() throws InterruptedException {
        if(position.x != focus.x) {
            if (position.x < focus.x)
                moveX(1);
            else
                moveX(-1);
            //this.map.set((int) this.position.x+100, (int) this.position.y+200,imagePosition);
        }
        if(position.y != focus.y){
            if(position.y < focus.y)
                moveY(1);
            else
                moveY(-1);
            //this.map.set((int) this.position.x+100, (int) this.position.y+200,imagePosition);
        }
    }


    void moveX(int direction) throws InterruptedException {
        this.position.x +=velocity*direction * speedMovement;
        //event.notify("data", transform());
    }
    void moveY(int direction) throws InterruptedException {
        this.position.y +=velocity*direction * speedMovement;
        //event.notify("data", transform());
    }

    public int getIdTeams() {
        return idTeams;
    }

    public int getIdType() {
        return idType;
    }

    public int getLife() {
        return life;
    }

    public PVector getPosition() {
        return position;
    }

    public int getDmgAttack() {
        return dmgAttack;
    }

    public float getFireRate() {
        return fireRate;
    }

    public float getSpeedMovement() {
        return speedMovement;
    }

    public void setIdTeams(int idTeams) {
        this.idTeams = idTeams;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void setPosition(PVector position) {
        this.position = position;
    }

    public void setDmgAttack(int dmgAttack) {
        this.dmgAttack = dmgAttack;
    }

    public void setFireRate(float fireRate) {
        this.fireRate = fireRate;
    }

    public void setSpeedMovement(int speedMovement) {
        this.speedMovement = speedMovement;
    }

    public PVector getFocus() {
        return focus;
    }

    public void setFocus(PVector focus) {
        this.focus = focus;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
