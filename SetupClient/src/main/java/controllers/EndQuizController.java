package controllers;

import interfaces.Game;
import interfaces.Quiz;
import interfaces.Server;
import views.EndQuizView;

import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Class to manage ending a running quiz & determining the winning game.
 *
 * @author Michael Bragg
 */
public class EndQuizController extends Controller {

    private final EndQuizView view;

    public EndQuizController(Server model, EndQuizView view) {
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
        List<Quiz> quizzes = model.getActiveQuizzes();
        if (quizzes.isEmpty()) {
            view.printNoActiveQuizzesMessage();
        } else {
            int quizId = getQuizFromUser(quizzes);

            List<Game> highscoreGames = model.setQuizInactive(quizId);

            if (highscoreGames.isEmpty()) {
                view.printNoPlayersMessage();
            } else {
                displayPlayers(highscoreGames);
            }
        }
    }

    private int getQuizFromUser(List<Quiz> quizzes) {
        int input;
        do {
            displayActiveQuizzes(quizzes);
            view.printQuizIDDeactivationRequest();

            try {
                input = view.getNextIntFromConsole();
                break;
            } catch (InputMismatchException e) {
                view.printException("Invalid input: only numbers are accepted. Please try again");
            } catch (NullPointerException e) {
                view.printException(e.getMessage());
            } finally {
                view.getNextLineFromConsole();
            }
        } while (true);
        return input;
    }

    private void displayActiveQuizzes(List<Quiz> quizzes) {
        for (Quiz quiz : quizzes) {
            view.printQuizDetails(quiz.getQuizID(), quiz.getQuizName());
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
