package setupClient;

import server.interfaces.Server;
import setupClient.factories.ControllerFactory;
import setupClient.factories.ViewFactory;

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
class SetupClientLauncher {

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

        ControllerFactory controllerFactory = ControllerFactory.getInstance();
        controllerFactory.setServer(server);

        ViewFactory viewFactory = ViewFactory.getInstance();

        controllerFactory.getMenuController(viewFactory.getMenuView(),
                viewFactory.getCreateQuizView(),
                viewFactory.getEndQuizView(),
                viewFactory.getDisplayQuizView())
                .launch();
    }
}