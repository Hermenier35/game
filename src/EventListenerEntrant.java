import processing.data.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class EventListenerEntrant implements Runnable{
    Socket socket;
    EventManager events;

    JSONObject data;

    public EventListenerEntrant(Socket socket) {
        this.socket = socket;
        events = new EventManager("data");
    }

    private boolean concatData(BufferedReader in){
        try {
            String rep = in.readLine();
            while(!rep.endsWith("}"))
                rep+=in.readLine();
            data = JSONObject.parse(rep);
            System.out.println(rep);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while(true) {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                if(concatData(in)) {
                    events.notify("data", data);
                    System.out.println("eventEntrant: " + data);
                }
            } catch (Exception e) {
                System.err.println("Erreur sérieuse : " + e);
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
}
