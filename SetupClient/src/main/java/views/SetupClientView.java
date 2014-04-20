package views;

import java.util.Scanner;

import static utilities.MessageProperties.msg;

/**
 * View for general messages required for the set-up client.
 *
 * @author Michael Bragg
 */
public abstract class SetupClientView {
	
	private final Scanner in = new Scanner(System.in);
	
	public void printInvalidInputMessage() {
		System.out.println(msg("invalid_input"));
	}
	
	public void printNoActiveQuizzesMessage() {
		System.out.println(msg("no_active_quizzes"));
	}

	public void printNoInActiveQuizzesMessage() {
        System.out.println(msg("no_inactive_quizzes"));
	}

	public void printException(String e) {
		System.out.println(e);
	}
	
	public void printInvalidInputException() {
        System.out.println(msg("invalid_input_format"));
	}
	
	public int getNextIntFromConsole() {
		return in.nextInt();
	}
	
	public String getNextLineFromConsole() {
		return in.nextLine();
	}

    public void printQuizDetails(int quizID, String quizName) {
        System.out.println(quizID + ": " + quizName);
    }
}