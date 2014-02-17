package setupClient.controllers;

import java.rmi.RemoteException;

import setupClient.views.MenuView;

public class MenuController extends Controller {
	
	private boolean running;
	private Controller createQuizController;
	private Controller viewActiveQuizesController;
	private Controller endQuizContoller;
	private MenuView view;
	
	public MenuController(Controller createContoller, Controller endContoller, Controller viewContoller, MenuView view ) {
		this.createQuizController 		= createContoller;
		this.endQuizContoller 			= endContoller;
		this.viewActiveQuizesController = viewContoller;
		this.view = view;
		running = true;
	}
	
	@Override
	public void launch() throws RemoteException  {
		view.printWelcomeMessage();
		while(running) {
			view.printMainMenu();
			selectAction();	
		} 
	}

	private void selectAction() throws RemoteException {

		String userChoice;
		userChoice = view.getNextLineFromConsole().trim();
		if (userChoice.toLowerCase().equals("exit")) {
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
	 * @param action int 1 to 3
	 */
	private void mainMenu(int action) throws RemoteException {
		switch(action) {
		case 1: 
			createQuizController.launch();
			break;
		case 2:
			viewActiveQuizesController.launch();
			break;
		case 3: 
			endQuizContoller.launch();
			break;
		default: 
			view.printInvalidInputMessage();
		}
	}
	
}