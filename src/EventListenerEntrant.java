import processing.data.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class EventListenerEntrant implements Runnable{
    Socket socket;
    JSONObject data;
    EventManager events;
    int id;

    public EventListenerEntrant(Socket socket, int id) {
        this.socket = socket;
        events = new EventManager("data");
        this.id = id;
    }

    @Override
    public void run() {
        while(true) {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                if(in.ready()) {
                   data = new JSONObject(in);
                   events.notify("data", data);
                }
            } catch (Exception e) {
                System.err.println("Erreur sérieuse : " + e);
                e.printStackTrace();
                //System.exit(1);
            }
        }
    }
}
