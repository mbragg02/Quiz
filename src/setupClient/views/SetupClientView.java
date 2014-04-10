package setupClient.views;

import java.util.Scanner;

/**
 * View for general messages required for the set-up client.
 *
 * @author Michael Bragg
 */
public abstract class SetupClientView {
	
	private final Scanner in = new Scanner(System.in);
	
	public void printInvalidInputMessage() {
		System.out.println("Not a valid option. Please try again");
	}
	
	public void printNoActiveQuizesMessage() {
		System.out.println("Currently no active quizzes");
	}
	
	public void printException(String e) {
		System.out.println(e);
	}
	
	public void printInvalidInputException() {
		System.out.println("Not a valid number");
	}
	
	public int getNextIntFromConsole() {
		return in.nextInt();
	}
	
	public String getNextLineFromConsole() {
		return in.nextLine();
	}

}