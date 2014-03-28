package setupClient;

import server.interfaces.Server;
import setupClient.controllers.ControllerFactory;
import setupClient.views.CreateQuizView;
import setupClient.views.DisplayQuizView;
import setupClient.views.EndQuizView;
import setupClient.views.MenuView;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Michael Bragg
 *         Set-up client launcher.
 *         Set-up client connects to the Quiz server to create quizzes.
 */
public class SetupClientLauncher {

    private static final String SERVER_ADDRESS = "//127.0.0.1:1099/quiz";

    public static void main(String[] args) throws RemoteException {
        SetupClientLauncher setup = new SetupClientLauncher();
        setup.launch();
    }

    private void launch() throws RemoteException {
        Remote service;
        try {
            service = Naming.lookup(SERVER_ADDRESS);
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            System.out.println("Can not find server at: " + SERVER_ADDRESS);
            return;
        }

        Server server = (Server) service;

        ControllerFactory factory = ControllerFactory.getInstance();
        factory.setServer(server);

        MenuView menuView               = new MenuView();
        CreateQuizView createQuizView   = new CreateQuizView();
        EndQuizView endQuizView         = new EndQuizView();
        DisplayQuizView displayQuizView = new DisplayQuizView();

        factory.getMenuController(menuView, createQuizView, endQuizView, displayQuizView).launch();
    }
}