import factories.Factory;
import factories.FileFactory;
import factories.QuizFactory;
import interfaces.Server;
import models.ServerData;
import utilities.DB;
import utilities.LoggerWrapper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.AccessControlException;
import java.util.Properties;
import java.util.logging.Level;

/**
 * Class to setup and launch the Quiz Server.
 *
 * @author Michael Bragg
 */
public class ServerLauncher {

    private static final String SERVER_PROPERTIES_FILE = "QuizServer/server.properties";
    private String serviceName;
    private int port;
    private Factory serverFactory;
    private FileFactory fileFactory;

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
        System.setProperty("java.security.policy", "QuizServer/security.policy");

        serverFactory = Factory.getInstance();
        fileFactory = FileFactory.getInstance();

        loadPropertiesFile();

        ServerData serverData = DB.read();

        Runtime.getRuntime().addShutdownHook(serverFactory.getShutdownHook(serverData));

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(serverFactory.getSecurityManager());
        }

        try {
            Server server = serverFactory.getServer(QuizFactory.getInstance(), serverData);

            Registry registry = LocateRegistry.createRegistry(port);
            registry.rebind(serviceName, server);

            LoggerWrapper.log(Level.INFO, "Server Started");

        } catch (AccessControlException e) {
            LoggerWrapper.log(Level.SEVERE, "Access Control Exception. Check that the security.policy file has been properly configured.");
        } catch (RemoteException ex) {
            LoggerWrapper.log(Level.SEVERE, ex.getMessage());
        }
    }

    private void loadPropertiesFile() {

        Properties props = serverFactory.getProperties();

        try {
            props.load(fileFactory.getFileInputStream(SERVER_PROPERTIES_FILE));
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