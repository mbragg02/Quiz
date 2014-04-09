package setupClient.controllers;

import server.interfaces.Quiz;
import server.interfaces.Server;
import setupClient.views.DisplayQuizView;

import java.rmi.RemoteException;
import java.util.List;

/**
 * @author Michael Bragg
 *         Class to manage displaying active quizzes.
 */
public class DisplayController extends Controller {

    private final DisplayQuizView view;

    public DisplayController(Server model, DisplayQuizView view) {
        super(model);
        this.view = view;
    }

    @Override
    public void launch() throws RemoteException {
        List<Quiz> quizzes = model.getActiveQuizzes();
        if (quizzes.isEmpty()) {
            view.printNoActiveQuizesMessage();
        } else {
            for (Quiz quiz : quizzes) {
                view.printQuizDetails(quiz.getQuizID(), quiz.getQuizName());
            }
        }
    }
}