package setupClient.controllers;

import java.rmi.RemoteException;
import java.util.Scanner;

import setupClient.views.MenuView;

public class MenuController extends Controller {
	
	private boolean running;
	private Scanner in;
	private Controller createQuizController;
	private Controller viewActiveQuizesController;
	private Controller endQuizContoller;
	private MenuView menuView;
	
	public MenuController(Controller create, Controller end, Controller view, MenuView menuView ) {
		this.createQuizController 		= create;
		this.endQuizContoller 			= end;
		this.viewActiveQuizesController = view;
		this.menuView = menuView;
		running = true;
		in = new Scanner(System.in);
	}
	
	@Override
	public void launch() throws RemoteException  {
		menuView.printWelcomeMessage();

		while(running) {
			menuView.printMainMenu();
			selectAction();	
		} 
		in.close();
	}


	private void selectAction() throws RemoteException {

		String userChoice;
		userChoice = in.nextLine().trim();
		if (userChoice.toLowerCase().equals("exit")) {
			running = false;
			menuView.printExitMessage();
		} else {
			mainMenuLaunch(userChoice);
		}
	}

	private void mainMenuLaunch(String userChoice) throws RemoteException {
		try {
			int action = Integer.parseInt(userChoice);
			mainMenu(action);
		} catch (NumberFormatException ex) {
			menuView.printInvalidInputMessage();
		} 
	}


	/**
	 * Main application menu
	 * @param action int 1 to 3
	 * @throws RemoteException 
	 */
	private void mainMenu(int action) throws RemoteException {
		switch(action) {
		case 1: 
			createQuizController.launch();;
			break;
		case 2:
			viewActiveQuizesController.launch();
			break;
		case 3: 
			endQuizContoller.launch();
			break;
		default: 
			menuView.printInvalidInputMessage();
		}
	}

}
