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
    protected int speedMovement;
    protected PApplet p;
    protected PImage map;
    protected EventManager event;
    protected  float velocity = 10f;

    public MovibleEntity(int idTeams, int idType, int life, PVector position, int dmgAttack, float fireRate, int speedMovement, PApplet p, PImage map, EventManager event) {
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
    }

    public abstract void draw() throws InterruptedException;
    public abstract void setup();
    public void deplacement() throws InterruptedException {
        if(position.x != focus.x) {
            if (position.x < focus.x)
                moveX(1);
            else
                moveY(-1);
        }
        if(position.y != focus.y){
            if(position.y < focus.y)
                moveY(1);
            else
                moveY(-1);
        }
    }


    void moveX(int direction) throws InterruptedException {
        this.position.x +=velocity*direction;
        event.notify("data", transform());
    }
    void moveY(int direction) throws InterruptedException {
        this.position.y +=velocity*direction;
        event.notify("data", transform());
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

    public int getSpeedMovement() {
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
}
