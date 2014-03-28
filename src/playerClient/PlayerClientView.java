package playerClient;

import java.util.Scanner;

public class PlayerClientView {
	
	private final Scanner in = new Scanner(System.in);
	
	public void displayWelcomeMessage() {
		System.out.println("Welcome to Quiz Player Client");
	}
	
	public void displayNoActiveQuizesMessage() {
		System.out.println("Currently no active quizzes. Please try again later.");
	}
	
	public void displayNameRequest() {
		System.out.print("Please enter your name: ");
	}
	
	public void displayCurrentActiveQuizesMessage() {
		System.out.println("Current active games: ");
	}
	
	public void displayQuizDetails(int quizID, String quizName) {
		System.out.println("Quiz " + quizID + ": " + quizName);
	}
	
	public void displaySelectQuizMessage() {
		System.out.print("Please select a quiz to play: ");
	}
	
	public void displayException(String e) {
		System.out.println(e);
	}
	
	public void displayInvlaidInputException() {
		System.out.println("Invalid input");
	}
	
	public void displayQuestion(int questionCount, String question) {
		System.out.println("Q" + questionCount + ": " + question);
	}
	
	public void displayAnswer(int answerCount, String answer) {
		System.out.println(" [" + answerCount + "] " + answer);
	}
	
	public void displayAnswerRequest() {
		System.out.print("Please select an answer: ");
	}
	
	public void displayScoreDetails(int score, int numberOfQuestions) {
		System.out.println("Your score was: " + score + " out of " + numberOfQuestions);

	}
	
	public int getNextIntFromConsole() {
		return in.nextInt();
	}
	
	public String getNextLineFromConsole() {
		return in.nextLine();
	}

}
