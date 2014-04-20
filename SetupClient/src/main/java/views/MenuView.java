package views;

import static utilities.MessageProperties.msg;

/**
 * View for the set-up clients main menu
 *
 * @author Michael Bragg
 */
public class MenuView extends SetupClientView{
	
	public void printMainMenu() {
        System.out.println(msg("main_menu"));
		System.out.println("> " + msg("menu_1"));
		System.out.println("> " + msg("menu_2"));
		System.out.println("> " + msg("menu_3"));
		System.out.println("> " + msg("menu_exit"));
		System.out.println(": ");
	}
	
	public void printWelcomeMessage() {
		System.out.println(msg("setup_welcome"));
	}
	
	public void printExitMessage() {
		System.out.println(msg("exit"));
	}
}