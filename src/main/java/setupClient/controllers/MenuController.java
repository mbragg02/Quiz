package setupClient.controllers;

import setupClient.views.MenuView;

import java.rmi.RemoteException;

/**
 * Main menu for the set-up client
 *
 * @author Michael Bragg
 */
public class MenuController extends Controller {

    private final Controller createQuizController;
    private final Controller startQuizController;
    private final Controller endQuizController;
    private final MenuView view;
    private boolean running;

    public MenuController(Controller createController, Controller endController, Controller startController, MenuView view) {
        this.createQuizController = createController;
        this.endQuizController    = endController;
        this.startQuizController  = startController;
        this.view = view;
        running = true;
    }

    @Override
    public void launch() throws RemoteException {
        view.printWelcomeMessage();
        while (running) {
            view.printMainMenu();
            selectAction();
        }
    }

    private void selectAction() throws RemoteException {

        String userChoice;
        userChoice = view.getNextLineFromConsole().trim();
        if (userChoice.equalsIgnoreCase("exit")) {
            running = false;
            view.printExitMessage();
        } else {
            mainMenuLaunch(userChoice);
        }
    }

    private void mainMenuLaunch(String userChoice) throws RemoteException {
        try {
            int action = Integer.parseInt(userChoice);
            mainMenu(action);
        } catch (NumberFormatException ex) {
            view.printInvalidInputMessage();
        }
    }

    /**
     * Main application menu
     *
     * @param action int 1 to 3
     */
    private void mainMenu(int action) throws RemoteException {
        switch (action) {
            case 1:
                createQuizController.launch();
                break;
            case 2:
                startQuizController.launch();
                break;
            case 3:
                endQuizController.launch();
                break;
            default:
                view.printInvalidInputMessage();
        }
    }
}