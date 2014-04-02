package server;

import server.Factories.FileFactory;
import server.Factories.QuizFactory;
import server.Factories.ServerFactory;
import server.interfaces.Server;
import server.models.ServerData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.*;

/**
 * @author Michael Bragg
 *         Class to setup and launch the Quiz Server.
 */
public class ServerLauncher {

    public static final String FILENAME = "serverData.txt";
    private static final String LOG_FILENAME = "server.log";
    private static final String SERVER_PROPERTIES_FILE = "server.properties";
    private static String registryHost;
    private static String serviceName;
    private static int port;
    private static ServerFactory serverFactory;
    private static FileFactory fileFactory;
    private ServerData serverData;
    private Logger logger;

    /**
     * Main method. Gets server properties from properties file needed before launch.
     *
     * @param args String[] none
     * @throws RemoteException
     */
    public static void main(String[] args) throws RemoteException {

        ServerLauncher main = new ServerLauncher();
        serverFactory = ServerFactory.getInstance();
        fileFactory = FileFactory.getInstance();

        Properties props = serverFactory.getProperties();

        try {
            props.load(fileFactory.getFileInputStream(SERVER_PROPERTIES_FILE));
            registryHost = props.getProperty("registryHost");
            serviceName = props.getProperty("serviceName");
            port = Integer.parseInt(props.getProperty("port"));
        } catch (FileNotFoundException e) {
            System.out.println("Server properties file not found.");
        } catch (IOException e) {
            System.out.println("Exception reading server properties file.");
        }

        main.launch();
    }

    /**
     * Method to launch the Quiz server.
     */
    private void launch() {
        InitializeLog(LOG_FILENAME);
        LoadServerData(FILENAME);

        Runtime.getRuntime().addShutdownHook(serverFactory.getShutdownHook(logger, serverData));

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(serverFactory.getRMISecurityManager());
        }
        try {
            LocateRegistry.createRegistry(port);
            Server server = serverFactory.getServer(QuizFactory.getInstance(), logger, serverData);
            Naming.rebind(registryHost + serviceName, server);
        } catch (MalformedURLException | RemoteException ex) {
            logger.severe(Arrays.toString(ex.getStackTrace()));
        }
        logger.info("Server Started");
    }

    /**
     * Initialize the logger for the server.
     *
     * @param fileName String. The name of the log file.
     */
    private void InitializeLog(String fileName) {
        logger = Logger.getLogger("QuizServerLog");

        // Remove the default logger from writing info log messages to the console.
        logger.setUseParentHandlers(false);

        try {
            // Handler for writing log messages to a file.
            Handler fileHandler = fileFactory.getFileHandler(fileName, true);
            SimpleFormatter formatter = serverFactory.getSimpleFormatter();
            fileHandler.setFormatter(formatter);
            fileHandler.setLevel(Level.FINE);

            // Handler for writing log messages to the console. i.e Exceptions
            Handler consoleHandler = serverFactory.getConsoleHandler();
            consoleHandler.setFormatter(formatter);
            consoleHandler.setLevel(Level.INFO);

            // Add the fileHandler and consoleHandler to the logger.
            logger.addHandler(fileHandler);
            logger.addHandler(consoleHandler);

        } catch (SecurityException | IOException e) {
            logger.severe(e.toString());
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
            logger.fine("Loading data from file: " + fileName);
            try (ObjectInputStream stream = fileFactory.getObjectInputStream(fileName)) {
                serverData = (ServerData) stream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                logger.warning(Arrays.toString(e.getStackTrace()));
            }
        } else {
            logger.info("Initialize server with zero quizzes/games");
            serverData = new ServerData();
        }
    }

}