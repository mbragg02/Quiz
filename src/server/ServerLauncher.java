package server;

public class ServerLauncher {

	public static void main(String[] args) {
		ServerLauncher server = new ServerLauncher();
		server.launch();
	}
	
	private void launch() {
		Server quizServer = new ServerImpl();
		quizServer.launch();
	}
	

}
