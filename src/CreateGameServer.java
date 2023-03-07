
public class CreateGameServer {
	Thread thread;
	
	public CreateGameServer() {
		this.thread = new Thread(new Server());
	}
	
	public void execute() {
		thread.start();
	}
	

}
