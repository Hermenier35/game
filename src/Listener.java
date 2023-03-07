import processing.data.JSONObject;

public  interface  Listener {
    void  update(String eventType, JSONObject data) throws InterruptedException;
}
