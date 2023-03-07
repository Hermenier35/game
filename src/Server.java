import processing.data.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/*creer un serveur qui ecoute les connexions entrantes pour le jeu
 pour chaque nouveau joueur un thread est créé pour gérer les données entre le serveur et le joueur 
*/
public class Server implements Runnable{
	private final int PORT = 1234;
	private final int NBRJOUEURMAX = 10;
	List<Joueur> joueurs;
	EventListenerServer eventListenerServer;
	Thread thread;
	Executor threadspoll = Executors.newFixedThreadPool(NBRJOUEURMAX);
	ReentrantLock reentrantLock;
	
	public Server() {
		joueurs = Collections.synchronizedList(new ArrayList<>());
		eventListenerServer = new EventListenerServer(joueurs);
		reentrantLock = new ReentrantLock();
	}
	
	@Override
	public void run() {
		runServer();
	}
	
	public synchronized void runServer() {
	
		try {
			ServerSocket socketAttente = new ServerSocket(PORT);
			thread = new Thread(eventListenerServer);
			thread.start();
			
			do {
				Socket clientSocket = socketAttente.accept();
				clientSocket.setTcpNoDelay(true);
				giveId(clientSocket);
				int id = joueurs.size();
				Joueur joueur = new Joueur(id, clientSocket, reentrantLock);
				threadspoll.execute(joueur);
				joueurs.add(joueur);
				joueur.manager.addListener("data", eventListenerServer);
			}while(joueurs.size() < NBRJOUEURMAX);
		}catch(Exception e){
			System.err.println("Erreur: " + e); 
			e.printStackTrace();
			System.exit(1);
		}
	} 
	
	public void giveId(Socket s) {
		JSONObject IDdata = new JSONObject();
		IDdata.setString("type", "attribution_id");
		IDdata.setInt("id", joueurs.size());
		try {
			PrintWriter out = new PrintWriter(s.getOutputStream());
			out.println(IDdata);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
