import processing.core.PVector;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class EventListenerSortant implements Listener, Runnable {
    protected Socket socket;
    protected int id;
    AtomicBoolean isChanged = new AtomicBoolean(true);

    public EventListenerSortant(Socket socket, int id){
        this.socket = socket;
        this.id = id;
    }
    @Override
    public void update(String eventType, Object arg) {
        switch(eventType){
           // case "position": this.position = (PVector) arg;break;
            default:break;
        }
        isChanged.set(true);
    }

    @Override
    public void run() {
        while(true) {
            if (isChanged.get()) {
                isChanged.set(false);
                try {
                    PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                  //  pw.println("position "+ id + " " + position.x + " " + position.y);
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
