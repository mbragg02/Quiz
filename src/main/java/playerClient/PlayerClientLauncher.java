package playerClient;

import playerClient.controllers.PlayerClientImpl;
import playerClient.interfaces.PlayerClient;
import playerClient.views.PlayerClientView;
import server.interfaces.Server;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Class for connecting to the Quiz serve and
 * launching the Quiz player client.
 *
 * @author Michael Bragg
 */
public class PlayerClientLauncher {

    public static void main(String[] args) throws RemoteException {
        PlayerClientLauncher client = new PlayerClientLauncher();
        client.launch();
    }

    private void launch() {

        try {

            Registry registry = LocateRegistry.getRegistry("localhost");

            Server server = (Server) registry.lookup("quizServer");

            PlayerClientView view = new PlayerClientView();

            PlayerClient client = new PlayerClientImpl(server, view);
            client.launch();

        } catch (NotBoundException | RemoteException e) {
            System.out.println("Can not find server at localhost");
        }

    }
}