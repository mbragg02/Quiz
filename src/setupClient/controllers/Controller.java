package setupClient.controllers;

import server.interfaces.Server;

public abstract class Controller {
	
	protected Server server;
	
	public Controller(Server server) {
		this.server = server;
	}
	
	public abstract void launch();

}
