import processing.data.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class EventListenerEntrant implements Runnable{
    Socket socket;
    JSONObject data;

    public EventListenerEntrant(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while(true) {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                data = JSONObject.parse(in.readLine());
                //System.out.println(data);
            } catch (Exception e) {
                System.err.println("Erreur sérieuse : " + e);
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
}
