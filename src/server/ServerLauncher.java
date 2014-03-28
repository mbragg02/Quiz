package server;

import server.interfaces.Server;
import server.models.ServerImpl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Properties;

public class ServerLauncher {
	private static String registryHost;
	private static String serviceName;

	public static void main(String[] args) throws RemoteException {
		Properties props = new Properties();

		try {
			props.load(new FileInputStream("server.properties"));
			registryHost = props.getProperty("registryHost");
			serviceName = props.getProperty("serviceName");
		} catch (FileNotFoundException e) {
			System.out.println("Server properties file not found.");
		} catch (IOException e) {
			System.out.println("Exception reading server properties file.");
		}

		launch();
	}

	private static void launch() {
		
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new RMISecurityManager());
		}
		try {
			LocateRegistry.createRegistry(1099);
			Server server = new ServerImpl();

			Naming.rebind(registryHost + serviceName, server);			
		} catch (MalformedURLException | RemoteException ex) {
			ex.printStackTrace();
		}
    }


}
