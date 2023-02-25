import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class EventListenerEntrant implements Runnable{
    Socket socket;

    public EventListenerEntrant(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while(true) {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String paquet = in.readLine();
                System.out.println(paquet);
            } catch (Exception e) {
                System.err.println("Erreur sérieuse : " + e);
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
}
