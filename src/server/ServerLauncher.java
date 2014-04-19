package server;

import server.factories.Factory;
import server.factories.FileFactory;
import server.factories.QuizFactory;
import server.interfaces.Server;
import server.models.ServerData;
import server.utilities.DB;
import server.utilities.LoggerWrapper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.security.AccessControlException;
import java.util.Properties;
import java.util.logging.Level;

/**
 * Class to setup and launch the Quiz Server.
 *
 * @author Michael Bragg
 */
public class ServerLauncher {

    public static final String FILENAME = "serverData.txt";
    private static final String SERVER_PROPERTIES_FILE = "server.properties";
    private String registryHost;
    private String serviceName;
    private int port;
    private Factory serverFactory;
    private FileFactory fileFactory;

    public ServerLauncher() {
        serverFactory = Factory.getInstance();
        fileFactory   = FileFactory.getInstance();
    }

    /**
     * Main method. Gets server properties from properties file needed before launch.
     *
     * @param args String[] none
     * @throws RemoteException
     */
    public static void main(String[] args) throws RemoteException {

        ServerLauncher main = new ServerLauncher();
        main.launch();
    }

    /**
     * Method to launch the Quiz server.
     */
    private void launch() {

        System.setProperty("java.security.policy", "security.policy");
        loadRMIPropertyFile();

        ServerData serverData = DB.read(FILENAME);
        Runtime.getRuntime().addShutdownHook(serverFactory.getShutdownHook(serverData));

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(serverFactory.getRMISecurityManager());
        }
        try {
            LocateRegistry.createRegistry(port);
            Server server = serverFactory.getServer(QuizFactory.getInstance(), serverData);
            Naming.rebind(registryHost + serviceName, server);
            LoggerWrapper.log(Level.INFO, "Server Started");
        } catch (AccessControlException e) {
            LoggerWrapper.log(Level.SEVERE, "Access Control Exception. Check that the security.policy file has been properly configured as a VM argument. See the readme for details");
        } catch (MalformedURLException | RemoteException ex) {
            LoggerWrapper.log(Level.SEVERE, ex.getMessage());
        }
    }

    private void loadRMIPropertyFile() {

        Properties props = serverFactory.getProperties();

        try {
            props.load(fileFactory.getFileInputStream(SERVER_PROPERTIES_FILE));
            registryHost = props.getProperty("registryHost");
            serviceName = props.getProperty("serviceName");
            port = Integer.parseInt(props.getProperty("port"));
        } catch (AccessControlException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println("Server properties file not found.");
        } catch (IOException e) {
            System.out.println("Exception reading server properties file.");
        }
    }

}