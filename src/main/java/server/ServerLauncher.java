package server;

import server.factories.Factory;
import server.factories.FileFactory;
import server.factories.QuizFactory;
import server.interfaces.Server;
import server.models.ServerData;
import server.utilities.LoggerWrapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.AccessControlException;
import java.util.Arrays;
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
    private static String registryHost;
    private static String serviceName;
    private static int port;
    private static Factory serverFactory;
    private static FileFactory fileFactory;
    private ServerData serverData;

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

        serverFactory = Factory.getInstance();
        fileFactory = FileFactory.getInstance();

        loadPropertiesFile();

        LoadServerData(FILENAME);

        Runtime.getRuntime().addShutdownHook(serverFactory.getShutdownHook(serverData));

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(serverFactory.getRMISecurityManager());
        }


        try {
            Server server = serverFactory.getServer(QuizFactory.getInstance(), serverData);

            Registry registry = LocateRegistry.createRegistry(port);
            registry.rebind(serviceName, server);

            LoggerWrapper.log(Level.INFO, "Server Started");

        } catch (AccessControlException e) {
            e.printStackTrace();
            //LoggerWrapper.log(Level.SEVERE, "Access Control Exception. Check that the security.policy file has been properly configured.");
        } catch (RemoteException ex) {
            LoggerWrapper.log(Level.SEVERE, ex.getMessage());
        }
    }

    private void loadPropertiesFile() {

        Properties props = serverFactory.getProperties();

        try {
            //URL url = getClass().getResource(SERVER_PROPERTIES_FILE);
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

    /**
     * Load the Quiz, Game and ID data from disk. If the file does not exists then
     * initiate a new empty data store.
     *
     * @param fileName String. Name of the server data file.
     */
    @SuppressWarnings("unchecked")
    private void LoadServerData(String fileName) {

        if (new File(fileName).exists()) {
            LoggerWrapper.log(Level.FINE, "Loading data from file: " + fileName);
            try (ObjectInputStream stream = fileFactory.getObjectInputStream(fileName)) {
                serverData = (ServerData) stream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                LoggerWrapper.log(Level.WARNING, Arrays.toString(e.getStackTrace()));

            }
        } else {
            LoggerWrapper.log(Level.INFO, "Initialize server with zero quizzes/games");
            serverData = new ServerData();
        }
    }

}