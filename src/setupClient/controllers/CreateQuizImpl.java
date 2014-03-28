package setupClient.controllers;

import server.interfaces.Question;
import server.interfaces.Server;
import setupClient.interfaces.CreateQuiz;
import setupClient.views.CreateQuizView;

import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.List;

/**
 * @author Michael Bragg
 *         Class to manage creating a new quiz.
 */
public class CreateQuizImpl extends Controller implements CreateQuiz {

    private final CreateQuizView view;
    private int quizID;

    public CreateQuizImpl(Server model, CreateQuizView view) {
        super(model);
        this.view = view;
    }

    @Override
    public void launch() throws RemoteException {
        createQuizWithName();
        addQuestionsAndAnswers();
        view.printQuizID(quizID);
        setQuizStatus();
    }

    @Override
    public void createQuizWithName() throws RemoteException, NullPointerException {
        do {
            view.printNameInputRequest();
            String quizName = view.getNextLineFromConsole().trim();
            try {
                this.quizID = model.createQuiz(quizName);
                break;
            } catch (IllegalArgumentException e) {
                view.printException(e.getMessage());
            }
        } while (true);
    }

    @Override
    public void addQuestionsAndAnswers() throws RemoteException {
        String userQuestion;
        do {
            view.printQuestionInputRequest();
            userQuestion = view.getNextLineFromConsole().trim();
            if (userQuestion.trim().equalsIgnoreCase("y")) {
                if (model.getQuizQuestionsAndAnswers(quizID).isEmpty()) {
                    view.printQuestionNumberException();
                } else {
                    break;
                }
            } else {
                int questionId;
                try {
                    questionId = model.addQuestionToQuiz(quizID, userQuestion);
                    addAnswers(questionId);
                } catch (IllegalArgumentException | NullPointerException e) {
                    view.printException(e.getMessage());
                }
            }
        } while (true);
    }

    /*
        Method to get answers to questions from the user.
     */
    private void addAnswers(int questionId) throws RemoteException, NullPointerException {
        String userInput;
        do {
            view.printAnswerInputRequest();
            userInput = view.getNextLineFromConsole().trim();
            if (userInput.trim().equalsIgnoreCase("y")) {
                if (model.getAnswersForQuestion(quizID, questionId).isEmpty()) {
                    view.printAnswerNumberException();
                } else {
                    break;
                }
            } else {
                try {
                    model.addAnswerToQuestion(quizID, questionId, userInput);
                } catch (IllegalArgumentException | NullPointerException e) {
                    view.printException(e.getMessage());
                }
            }
        } while (true);

        selectCorrectAnswer(questionId);
    }


    @Override
    public void selectCorrectAnswer(int questionId) throws RemoteException, NullPointerException {
        do {
            view.printCorrectAnswerRequest();
            displayQuestionsAndAnswers(questionId);

            try {
                int correctAnswer = view.getNextIntFromConsole();
                model.setCorrectAnswer(quizID, questionId, correctAnswer - 1);
                break;
            } catch (InputMismatchException e) {
                view.printInvalidInputException();
            } catch (IllegalArgumentException e) {
                view.printException(e.getMessage());
            } finally {
                view.getNextLineFromConsole();
            }
        } while (true);
    }

    /*
        Method logic for displaying questions and answers.
     */
    protected void displayQuestionsAndAnswers(int questionID) throws RemoteException, NullPointerException {
        List<Question> questions = model.getQuizQuestionsAndAnswers(quizID);
        for (Question question : questions) {
            if (question.getQuestionID() == questionID) {
                view.printQuestion(question.getQuestion());
                List<String> answers = question.getAnswers();
                int answerCount = 1;
                for (String answer : answers) {
                    view.printAnswer(answerCount, answer);
                    ++answerCount;
                }
            }
        }
    }

    @Override
    public void setQuizStatus() throws RemoteException {
        view.printActivationRequest();
        String choice = view.getNextLineFromConsole();
        if (choice.trim().equalsIgnoreCase("y")) {
            model.setQuizActive(quizID);
            view.printActiveMessage();
        } else {
            view.printInActiveMessage();
        }
    }
}