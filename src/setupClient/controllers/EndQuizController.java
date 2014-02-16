package setupClient.controllers;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;

import server.interfaces.Game;
import server.interfaces.Server;

public class EndQuizController extends Controller{
	private Scanner in;
	
	public EndQuizController(Server server) {
		super(server);
		in = new Scanner(System.in);
	}
	
	@Override
	public void launch() throws RemoteException, IllegalArgumentException, NullPointerException {
		deactivateQuiz();
		
	}
	
	public void deactivateQuiz() throws RemoteException, IllegalArgumentException, NullPointerException {
		if (server.getActiveQuizes().isEmpty()) {
			System.out.println("Currently no active quizzes");
		} else {
			System.out.print("Please enter a Quiz ID to deactivate: ");
			int input = in.nextInt();
			in.nextLine();

			List<Game> highscoreGames = server.setQuizInactive(input);
			if (highscoreGames.isEmpty()) {
				System.out.println("There were no players that took the quiz");
			} else {
				displayPlayers(highscoreGames);
			}	
		}
	}

	private void displayPlayers(List<Game> games) {
		System.out.println("And the winners are...");
		int winnerCount = 1;
		for (Game game : games) {
			System.out.println(formatWinner(game, winnerCount));
			++winnerCount;
		}
		System.out.println("\n");
	}
	
	private String formatWinner(Game game, int winnerCount) {
		StringBuilder sb = new StringBuilder();
		sb.append("[" + winnerCount + "] " + game.getPlayerName());
		sb.append(" with a score of: " + calculatePercentage(game.getScore(), game.getNumberOfQuestions()) + "%");
		sb.append(" on " + game.getDateCompleted());
		return sb.toString();
	}
	
	private double calculatePercentage(int score, int total) {
		double result = 1d* score/total;
		result = result * 100;
		return result;
	}
}
