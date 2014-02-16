package setupClient;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import server.interfaces.Server;
import setupClient.controllers.*;
import setupClient.views.CreateQuizView;
import setupClient.views.EndQuizView;
import setupClient.views.MenuView;
import setupClient.views.ViewQuizView;


public class SetupClientLauncher {

	public static void main(String[] args) throws RemoteException {
		SetupClientLauncher setup = new SetupClientLauncher();
		setup.launch();

	}
	
	private void launch() throws RemoteException {
		Remote service = null;
		String serverAddress = "//127.0.0.1:1099/quiz";
		try {
			service = Naming.lookup(serverAddress);
		} catch (NotBoundException | MalformedURLException | RemoteException e) {
			System.out.println("Can not find server at: " + serverAddress);
			return;
		}
		
		Server server = (Server) service;
		
		ControllerFactory factory = ControllerFactory.getInstance();
		factory.setServer(server);
				
		MenuView menuView = new MenuView();
		CreateQuizView createView = new CreateQuizView();
		EndQuizView endView = new EndQuizView();
		ViewQuizView view = new ViewQuizView();
		
		factory.getMenuController(menuView, createView, endView, view).launch();
	}
	

}
