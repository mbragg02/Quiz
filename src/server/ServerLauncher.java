package server;

import server.Factories.Factory;
import server.interfaces.Server;
import server.models.Data;
import server.models.ServerImpl;
import server.utilities.ShutdownHook;

import java.io.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * @author Michael Bragg
 *         Class to setup and launch the Quiz Server.
 */
public class ServerLauncher {

    public static final String FILENAME = "serverData.txt";
    private static final String LOG_FILENAME = "server.log";
    private static String registryHost;
    private static String serviceName;
    private static int port;
    private Data data;
    private Logger logger;
    private static Factory factory;


    public static void main(String[] args) throws RemoteException {

        ServerLauncher main = new ServerLauncher();
        factory = Factory.getInstance();

        Properties props = factory.getProperties();

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

        main.launch();
    }

    private void launch() {
        InitializeLog();
        LoadServerData();

        Runtime.getRuntime().addShutdownHook(new ShutdownHook(logger, data));

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }
        try {
            LocateRegistry.createRegistry(port);
            Server server = new ServerImpl(factory, logger, data);
            Naming.rebind(registryHost + serviceName, server);
            logger.info("Server Started");
        } catch (MalformedURLException | RemoteException ex) {
            ex.printStackTrace();
        }
    }

    private void InitializeLog() {
        logger = Logger.getLogger("QuizServerLog");

        try {
            FileHandler fileHandler = new FileHandler(LOG_FILENAME, true);
            logger.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
        } catch (SecurityException | IOException e) {
            logger.severe(e.toString());
        }
    }

    @SuppressWarnings("unchecked")
    private void LoadServerData() {
        if (new File(FILENAME).exists()) {
            logger.info("Loading data from file: " + FILENAME);
            try (ObjectInputStream d = new ObjectInputStream(new BufferedInputStream(new FileInputStream(FILENAME)))) {
                data = (Data) d.readObject();
            } catch (IOException | ClassNotFoundException e) {
                logger.warning(Arrays.toString(e.getStackTrace()));
            }
        } else {
            logger.info("Initialize server with zero quizzes/games");
            data = new Data();
        }
    }

}