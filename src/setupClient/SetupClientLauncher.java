package setupClient;

import server.interfaces.Server;
import server.models.ServerImpl;
import setupClient.controllers.*;


public class SetupClientLauncher {

	public static void main(String[] args) {
		SetupClientLauncher setup = new SetupClientLauncher();
		setup.launch();

	}
	
	private void launch() {
		Server server = new ServerImpl();
		
		Controller create = new CreateQuizController(server);
		Controller end =    new EndQuizController(server);
		Controller view =   new ViewQuizController(server);

		MainMenu menu = new MainMenu(create, end, view);
		menu.launch();
	}
	

}
