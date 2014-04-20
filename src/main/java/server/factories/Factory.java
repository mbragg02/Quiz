package server.factories;

import server.models.ServerData;
import server.models.ServerImpl;
import server.utilities.ShutdownHook;

import java.rmi.RemoteException;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.SimpleFormatter;

/**
 * Factory to supply objects for the setup of the Quiz server.
 *
 * @author Michael Bragg
 */
public class Factory {

    private static Factory ServerFactory;


    private Factory() {
        // Private empty constructor
    }


    public static Factory getInstance() {
        if (ServerFactory == null) {
            ServerFactory = new Factory();
        }
        return ServerFactory;
    }

    public Properties getProperties() {
        return new Properties();
    }

    public ShutdownHook getShutdownHook(ServerData serverData) {
        return new ShutdownHook(serverData);
    }

    public SecurityManager getRMISecurityManager() {
        return new SecurityManager();
    }

    public ServerImpl getServer(QuizFactory quizFactory, ServerData serverData) throws RemoteException {
        return new ServerImpl(quizFactory, serverData);
    }

    public SimpleFormatter getSimpleFormatter() {
        return new SimpleFormatter();
    }

    public Handler getConsoleHandler() {
        return new ConsoleHandler();
    }
}
