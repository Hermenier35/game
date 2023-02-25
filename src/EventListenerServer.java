import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class EventListenerServer implements Runnable, Listener{
    ArrayList<Joueur> listJoueurs;
    AtomicBoolean isChanged = new AtomicBoolean(true);
    static int id;
    static double x, y;

    public EventListenerServer(ArrayList<Joueur> joueurs) {
        this.listJoueurs = joueurs;
    }

    @Override
    public void update(String eventType, Object arg) {
        String value[] = (String[]) arg;
        id = Integer.parseInt(value[1]);
        x = Double.parseDouble(value[2]);
        y = Double.parseDouble(value[3]);
        isChanged.set(true);
    }

    @Override
    public void run() {
        while(true){
            if(isChanged.get()) {
                isChanged.set(false);
                try {
                    for (Joueur j : listJoueurs) {
                        if (j.id != id) {
                            PrintWriter pw = new PrintWriter(new OutputStreamWriter(j.socket.getOutputStream()));
                            pw.println("position " + id + " " + x + " " + y);
                            pw.flush();
                        } else
                            System.out.println("modif pour ts les joueurs sauf :" + id + " " + x + " " + y);
                    }
                } catch (Exception e) {
                    System.err.println("Erreur sérieuse : " + e);
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }
    }
}
