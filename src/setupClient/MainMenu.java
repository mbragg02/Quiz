package setupClient;

import java.rmi.RemoteException;
import java.util.Scanner;

import setupClient.controllers.Controller;

public class MainMenu {
	
	private boolean running;
	private Scanner in;
	private Controller create;
	private Controller view;
	private Controller end;
	
	public MainMenu(Controller create, Controller end, Controller view ) {
		this.create = create;
		this.end = end;
		this.view = view;
		running = true;
		in = new Scanner(System.in);
	}

	
	public void launch()  {
		System.out.println("Welcome to Quiz Setup Client");
		while(running) {
			displayActions();
			selectAction();	
		} 
		in.close();
	}

	private void displayActions(){
		System.out.println("  Main Menu");
		System.out.println("> Enter 1 to create a new Quiz");
		System.out.println("> Enter 2 to view all current quizzes");
		System.out.println("> Enter 3 to end a running Quiz");
		System.out.println("> Enter EXIT to disconnect");
		System.out.println(": ");
	}

	private void selectAction() {

		String userChoice;
		userChoice = in.nextLine().trim();
		if (userChoice.toLowerCase().equals("exit")) {
			running = false;
			System.out.println("Exit succesfull");
		} else {
			mainMenuLaunch(userChoice);
		}
	}

	private void mainMenuLaunch(String userChoice) {
		try {
			int action = Integer.parseInt(userChoice);
			mainMenu(action);
		} catch (NumberFormatException ex) {
			System.out.println("Not a valid option. Please try again");
		} catch (IllegalArgumentException ex) {
			System.out.println(ex.getMessage());
		}
	}


	/**
	 * Main application menu
	 * @param action int 1 to 3
	 * @throws RemoteException 
	 * @throws IllegalArgumentException. Input must be between 1 and 3
	 */
	private void mainMenu(int action) {
		switch(action) {
		case 1: 
			create.launch();;
			break;
		case 2:
			end.launch();
			break;
		case 3: 
			view.launch();
			break;
		default: 
			throw new IllegalArgumentException("Input must be between 1 and 3");
		}
	}

}
