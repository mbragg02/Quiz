package playerClient;

import playerClient.controllers.PlayerClientImpl;
import playerClient.interfaces.PlayerClient;
import playerClient.views.PlayerClientView;
import server.interfaces.Server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Class for connecting to the Quiz serve and
 * launching the Quiz player client.
 *
 * @author Michael Bragg
 */
class PlayerClientLauncher {

    private static final String SERVER_ADDRESS = "//127.0.0.1:1099/quiz";

    public static void main(String[] args) throws RemoteException {
        PlayerClientLauncher client = new PlayerClientLauncher();
        client.launch();
    }

    private void launch() throws RemoteException {

        Remote service;
        try {
            service = Naming.lookup(SERVER_ADDRESS);
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            System.out.println("Can not find server at: " + SERVER_ADDRESS);
            return;
        }
        Server model = (Server) service;
        PlayerClientView view = new PlayerClientView();

        PlayerClient client = new PlayerClientImpl(model, view);
        client.launch();
    }
}