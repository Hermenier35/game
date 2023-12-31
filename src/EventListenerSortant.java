import processing.data.JSONObject;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class EventListenerSortant implements Listener, Runnable {
    protected Socket socket;

    public List<JSONObject> data;

    public EventListenerSortant(Socket socket){
        this.socket = socket;
        this.data = Collections.synchronizedList( new LinkedList<>());
    }
    @Override
    public void  update(String eventType, JSONObject data) {
        this.data.add(data);
    }

    @Override
    public void run() {
        while(true) {
            if (!this.data.isEmpty()){
                try {
                    PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                    pw.println(this.data.get(0));
                    System.out.println("pw :" + data.get(0));
                    pw.flush();
                    data.remove(0);
                } catch (Exception e) {
                    System.err.println("Erreur sérieuse : " + e);
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }
    }
}
