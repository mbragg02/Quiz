package playerClient;

import playerClient.controllers.PlayerClientImpl;
import playerClient.views.PlayerClientView;
import server.interfaces.Server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Michael Bragg
 *         Class for launching the Quiz player client.
 */
public class PlayerClientLauncher {

    public static final String SERVER_ADDRESS = "//127.0.0.1:1099/quiz";

    public static void main(String[] args) throws RemoteException {
        PlayerClientLauncher client = new PlayerClientLauncher();
        client.launch();
    }

    private void launch() throws RemoteException {

        Remote service;
        String serverAddress = SERVER_ADDRESS;
        try {
            service = Naming.lookup(serverAddress);
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            System.out.println("Can not find server at: " + serverAddress);
            return;
        }
        Server model = (Server) service;
        PlayerClientView view = new PlayerClientView();

        PlayerClientController client = new PlayerClientImpl(model, view);
        client.launch();
    }
}