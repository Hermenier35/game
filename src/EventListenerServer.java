import processing.data.JSONObject;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class EventListenerServer implements Runnable, Listener{
    List<Joueur> listJoueurs;
    List<JSONObject> datas;
    Server server;

    public EventListenerServer(List<Joueur> joueurs, Server server) {

        this.listJoueurs = joueurs;
        this.datas = Collections.synchronizedList( new LinkedList<>());
        this.server = server;
    }

    @Override
    public void update(String eventType, JSONObject data) {
        System.out.println("recu :" + data);
        this.datas.add(data);
    }

    @Override
    public synchronized void run() {
        while(true){
            if(!datas.isEmpty()) {
                try {
                    JSONObject data = datas.get(0);
                    if(data.getString("type").equals("startGame") && !server.gameStart)
                        server.gameStart = true;
                    for (Joueur j : listJoueurs) {
                       if (j.id != data.getInt("id")) {
                            PrintWriter pw = new PrintWriter(new OutputStreamWriter(j.socket.getOutputStream()));
                            pw.println(data);
                            System.out.println("envoi du serv  data venant de " + data.getString("pseudo") + " pour :" + j.pseudo);
                            pw.flush();
                        }
                    }
                    datas.remove(0);
                } catch (Exception e) {
                    System.err.println("Erreur sérieuse : " + e);
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }
    }
}
