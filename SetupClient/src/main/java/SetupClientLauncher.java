import factories.ControllerFactory;
import factories.ViewFactory;
import interfaces.Server;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Set-up client launcher.
 * Set-up client connects to the Quiz server to create quizzes.
 *
 * @author Michael Bragg
 */
public class SetupClientLauncher {

    public static void main(String[] args) throws RemoteException {
        SetupClientLauncher setup = new SetupClientLauncher();
        setup.launch();
    }

    private void launch() throws RemoteException {

        System.setProperty("java.security.policy", "SetupClient/security.policy");

        try {

            Registry registry = LocateRegistry.getRegistry("localhost");

            Server server = (Server) registry.lookup("quizServer");

            ControllerFactory controllerFactory = ControllerFactory.getInstance();
            controllerFactory.setServer(server);

            ViewFactory viewFactory = ViewFactory.getInstance();

            controllerFactory.getMenuController(viewFactory.getMenuView(),
                    viewFactory.getCreateQuizView(),
                    viewFactory.getEndQuizView(),
                    viewFactory.getStartQuizView())
                    .launch();


        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}