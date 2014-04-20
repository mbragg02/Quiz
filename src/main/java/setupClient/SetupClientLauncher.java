package setupClient;

import server.interfaces.Server;
import setupClient.factories.ControllerFactory;
import setupClient.factories.ViewFactory;

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
class SetupClientLauncher {

    private static final String SERVER_ADDRESS = "//127.0.0.1:1099/quiz";

    public static void main(String[] args) throws RemoteException {
        SetupClientLauncher setup = new SetupClientLauncher();
        setup.launch();
    }

    private void launch() throws RemoteException {

        Registry registry;
        try {

//              Remote service = Naming.lookup("//localhost:1099/SERVER");
//            Server server = (Server) service;

            registry = LocateRegistry.getRegistry("localhost");

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


//        Remote service;
//        try {
//            service = Naming.lookup(SERVER_ADDRESS);
//        } catch (NotBoundException | MalformedURLException | RemoteException e) {
//            System.out.println("Can not find server at: " + SERVER_ADDRESS);
//            return;
//        }
//
//        Server server = (Server) service;


    }
}