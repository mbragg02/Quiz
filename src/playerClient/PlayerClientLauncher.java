package playerClient;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import server.interfaces.Server;

public class PlayerClientLauncher {

	public static void main(String[] args) throws RemoteException {
		PlayerClientLauncher client = new PlayerClientLauncher();
		client.launch();

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
		Server model = (Server) service;
		PlayerClientView view = new PlayerClientView();
		
		PlayerClientController client = new PlayerClientControllerImpl(model, view);
		client.launch();
	}

}
