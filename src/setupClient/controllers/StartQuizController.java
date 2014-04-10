package setupClient.controllers;

import server.interfaces.Quiz;
import server.interfaces.Server;
import setupClient.views.StartQuizView;

import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Controller to start a game. i.e Make a Quiz ACTIVE
 *
 * @author Michael Bragg
 */
public class StartQuizController extends Controller {

    private final StartQuizView view;

    public StartQuizController(Server model, StartQuizView view) {
        super(model);
        this.view = view;
    }

    /**
     * Launch method to run a controller action.
     *
     * @throws java.rmi.RemoteException
     */
    @Override
    public void launch() throws RemoteException {
        
        if(model.getInactiveQuizzes().isEmpty()) {
            view.printNoInActiveQuizesMessage();
        } else {
         displayInactiveQuizzes();
        }
    }

    private void displayInactiveQuizzes() throws RemoteException {
        List<Quiz> quizzes = model.getInactiveQuizzes();

        do {

            for (Quiz quiz : quizzes) {
                view.printQuizDetails(quiz.getQuizID(), quiz.getQuizName());
            }

            int quizId = getQuizID();

            try {
                model.setQuizActive(quizId);
                view.printActivationSuccess();
                break;
            } catch (NullPointerException e) {
                view.printException(e.getMessage() + " Please try again:");
            }

        } while(true);
    }

    private int getQuizID() {
        int quizId;
        do {
            view.printQuizIDActivationRequest();
            try {
                quizId = view.getNextIntFromConsole();
                break;
            } catch(InputMismatchException e) {
                view.printException("Invalid input: Only numbers are excepted. Please try again");
            } finally {
                view.getNextLineFromConsole();
            }
        } while(true);
        return quizId;

    }
}
