package server;

import server.interfaces.Server;
import server.models.ServerImpl;
import server.Factories.Factory;
import server.Factories.IDFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Properties;

/**
 * @author Michael Bragg
 * Class to setup and launch the Quiz Server.
 */
public class ServerLauncher {

    private static String registryHost;
    private static String serviceName;
    private static int port;

    public static void main(String[] args) throws RemoteException {
        Properties props = new Properties();

        try {
            props.load(new FileInputStream("server.properties"));
            registryHost = props.getProperty("registryHost");
            serviceName = props.getProperty("serviceName");
            port = Integer.parseInt(props.getProperty("port"));
        } catch (FileNotFoundException e) {
            System.out.println("Server properties file not found.");
        } catch (IOException e) {
            System.out.println("Exception reading server properties file.");
        }

        launch();
    }

    private static void launch() {
        IDFactory idFact = IDFactory.getInstance();
        Factory fact = Factory.getInstance();

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }
        try {
            LocateRegistry.createRegistry(port);
            Server server = new ServerImpl(fact, idFact);

            Naming.rebind(registryHost + serviceName, server);
        } catch (MalformedURLException | RemoteException ex) {
            ex.printStackTrace();
        }
    }
}