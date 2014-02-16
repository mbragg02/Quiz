package setupClient.controllers;

import java.rmi.RemoteException;

import server.interfaces.Server;

public abstract class Controller {
	
	protected Server model;
	
	public Controller() {}
	
	public Controller(Server model) {
		this.model = model;
	}
	
	public abstract void launch() throws RemoteException ;

}
