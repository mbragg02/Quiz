package setupClient.controllers;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;

import server.interfaces.Game;
import server.interfaces.Server;
import setupClient.views.EndQuizView;

public class EndQuizController extends Controller{
	private Scanner in;
	private EndQuizView view;
	
	public EndQuizController(Server model, EndQuizView view) {
		super(model);
		this.view = view;
		in = new Scanner(System.in);
	}
	
	@Override
	public void launch() throws RemoteException, IllegalArgumentException, NullPointerException {
		if (model.getActiveQuizes().isEmpty()) {
			view.printNoActiveQuizesMessage();
		} else {
			view.printQuizIDDeactivationRequest();
			int input = in.nextInt();
			in.nextLine();

			List<Game> highscoreGames = model.setQuizInactive(input);
			if (highscoreGames.isEmpty()) {
				view.printNoPlayersMessage();
			} else {
				displayPlayers(highscoreGames);
			}	
		}
		
	}

	private void displayPlayers(List<Game> games) {
		view.printWinnersAreMessage();
		int winnerCount = 1;
		for (Game game : games) {
			view.printWinnerDetails(winnerCount, game.getPlayerName(), calcPercent(game.getScore(), game.getNumberOfQuestions()), game.getDateCompleted());
			++winnerCount;
		}
	}
	
	private double calcPercent(int score, int total) {
		double result = 1d* score/total;
		result = result * 100;
		return result;
	}
}
