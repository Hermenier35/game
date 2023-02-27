import processing.data.JSONObject;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class EventListenerServer implements Runnable, Listener{
    ArrayList<Joueur> listJoueurs;
    AtomicBoolean isChanged = new AtomicBoolean(true);
    JSONObject data;

    public EventListenerServer(ArrayList<Joueur> joueurs) {
        this.listJoueurs = joueurs;
    }

    @Override
    public void update(String eventType, JSONObject data) {
        this.data = data;
        isChanged.set(true);
    }

    @Override
    public void run() {
        while(true){
            if(isChanged.get()) {
                try {
                    for (Joueur j : listJoueurs) {
                        if (j.id != data.getInt("id")) {
                            PrintWriter pw = new PrintWriter(new OutputStreamWriter(j.socket.getOutputStream()));
                            pw.println(this.data.toString());
                            pw.flush();
                        }
                    }
                    isChanged.set(false);
                } catch (Exception e) {
                    System.err.println("Erreur sérieuse : " + e);
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }
    }
}
