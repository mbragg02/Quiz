package setupClient.controllers;

import server.interfaces.Game;
import server.interfaces.Server;
import setupClient.views.EndQuizView;

import java.rmi.RemoteException;
import java.util.List;

/**
 * @author Michael Bragg
 *         Class to manage ending a running quiz & determining the winning game.
 */
public class EndController extends Controller {
    private final EndQuizView view;

    public EndController(Server model, EndQuizView view) {
        super(model);
        this.view = view;
    }

    /**
     * Sets a active Quiz to inactive & calls methods to display the winner.
     *
     * @throws RemoteException
     */
    @Override
    public void launch() throws RemoteException {
        if (model.getActiveQuizzes().isEmpty()) {
            view.printNoActiveQuizesMessage();
        } else {
            view.printQuizIDDeactivationRequest();
            int input = view.getNextIntFromConsole();
            view.getNextLineFromConsole();

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
        double result = 1d * score / total;
        result = result * 100;
        return result;
    }
}
