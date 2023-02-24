import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.Socket;

public class Client {

	public static final int PORT = 1234;
	public void lanceClient() {
		try {
			Inet4Address address = (Inet4Address) Inet4Address.getByName("10.188.82.246");
			Socket service = new Socket(address,PORT);//46.193.64.23
			
			
			PrintWriter pw = new PrintWriter(
					new OutputStreamWriter(service.getOutputStream()));
			pw.println(12 + " " + 15);
			pw.flush();
			BufferedReader bf = new BufferedReader(
					new InputStreamReader(service.getInputStream()));
			String message = bf.readLine();
			System.out.println("Je viens de recevoir le message : "+message);

		} catch(Exception e) {
			System.err.println("Erreur sérieuse : "+e);
			e.printStackTrace(); System.exit(1); 


		}
	}
}
