import processing.data.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class EventListenerEntrant implements Runnable{
    Socket socket;
    JSONObject data;
    EventManager events;

    public EventListenerEntrant(Socket socket) {
        this.socket = socket;
        events = new EventManager("data");
    }

    @Override
    public void run() {
        while(true) {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                data = new JSONObject(in);
                events.notify("data", data);
            } catch (Exception e) {
                System.err.println("Erreur sérieuse : " + e);
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
}