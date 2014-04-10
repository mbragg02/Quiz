package setupClient.views;

/**
 * View for the set-up clients main menu
 *
 * @author Michael Bragg
 */
public class MenuView extends SetupClientView{
	
	public void printMainMenu() {
		System.out.println("  Main Menu");
		System.out.println("> Enter 1 to create a new Quiz");
		System.out.println("> Enter 2 to view all current quizzes");
		System.out.println("> Enter 3 to end a running Quiz");
		System.out.println("> Enter EXIT to disconnect");
		System.out.println(": ");
	}
	
	public void printWelcomeMessage() {
		System.out.println("Welcome to Quiz Setup Client");
	}
	
	public void printExitMessage() {
		System.out.println("Exit successful");
	}
}