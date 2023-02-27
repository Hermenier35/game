import processing.data.JSONObject;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class EventListenerSortant implements Listener, Runnable {
    protected Socket socket;
    protected int id;
    AtomicBoolean isChanged = new AtomicBoolean(true);

    JSONObject data;

    public EventListenerSortant(Socket socket, int id){
        this.socket = socket;
        this.id = id;
    }
    @Override
    public void update(String eventType, JSONObject data) {
        this.data = data;
        isChanged.set(true);
    }

    @Override
    public void run() {
        while(true) {
            if (isChanged.get()) {
                isChanged.set(false);
                try {
                    PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                    pw.println(this.data);
                    pw.flush();
                } catch (Exception e) {
                    System.err.println("Erreur sérieuse : " + e);
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }
    }
}
