
public class CreateGameServer {

	private Server server;
	Thread thread;
	
	public CreateGameServer() {
		this.server = new Server();
		this.thread = new Thread(server);
	}
	
	public void execute() {
		thread.start(); 
	}
	

}
