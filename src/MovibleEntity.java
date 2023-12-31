import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.data.JSONObject;

public abstract class MovibleEntity implements ConvertJson{
    protected int idTeam;
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

    public MovibleEntity(int idTeam, int idType, int life, PVector position, int dmgAttack, float fireRate, float speedMovement, PApplet p, PImage map, EventManager event, PVector camera) {
        this.idTeam = idTeam;
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
        imagePosition = new PImage();
    }

    public abstract void draw() throws InterruptedException;
    public abstract void setup();

    @Override
    public abstract JSONObject transform();

    public void deplacement() throws InterruptedException {
        if(!goalAchieved()) {
            PVector vector = new PVector( focus.x - position.x, focus.y - position.y);
            vector.normalize();
            position.add(vector);
        }
    }

    private boolean goalAchieved(){
        return Math.abs(position.x - focus.x) + Math.abs(position.y- focus.y) < 2;
    }

    public int getIdTeams() {
        return idTeam;
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

    public void setIdTeams(int idTeam) {
        this.idTeam = idTeam;
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
        try {
            event.notify("data", transform());
        }catch (Exception e){
            System.out.println("Erreur notify jeep_movement : " + e);
        }
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
