package setupClient.controllers;

import server.interfaces.Question;
import server.interfaces.Server;
import setupClient.views.CreateQuizView;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Class to manage creating a new quiz.
 *
 * @author Michael Bragg
 */
public class CreateControllerImpl extends Controller implements CreateController {

    private final CreateQuizView view;
    private int quizID;
    private int questionID;
    private boolean buildComplete;
    private boolean questionComplete;

    public CreateControllerImpl(Server model, CreateQuizView view) {
        super(model);
        this.view = view;
        this.quizID = 0;

    }

    @Override
    public void launch() throws RemoteException {
        quizBuilder();
        view.printQuizID(quizID);
        setQuizStatus();
    }

    /*
    Method calls and logic for building a complete new quiz
     */
    private void quizBuilder() throws RemoteException {
        buildComplete = false;
        createQuizWithName();
        do {
            addQuestion();
            if (!buildComplete) {
                addAnswers(questionID);
                selectCorrectAnswer(questionID);
            }

        } while (!buildComplete);
    }

    @Override
    public void createQuizWithName() throws RemoteException {
        do {
            view.printNameInputRequest();
            String quizName = view.getNextLineFromConsole().trim();
            try {
                this.quizID = model.createQuiz(quizName);
                break;
            } catch (IllegalArgumentException | NullPointerException e) {
                view.printException(e.getMessage());
            }
        } while (true);
    }

    @Override
    public void addQuestion() throws RemoteException {
        String userQuestion;
        questionComplete = false;
        do {
            view.printQuestionInputRequest();
            userQuestion = view.getNextLineFromConsole().trim();
            if (userQuestion.trim().equalsIgnoreCase("y")) {
                if (model.getQuizQuestionsAndAnswers(quizID).isEmpty()) {
                    view.printQuestionNumberException();
                } else {
                    buildComplete = true;
                    break;
                }
            } else {
                addQuestionToServer(userQuestion);
            }
        } while (!questionComplete);
    }

    private void addQuestionToServer(String userQuestion) {
        try {
            this.questionID = model.addQuestionToQuiz(quizID, userQuestion);
            questionComplete = true;
        } catch (IllegalArgumentException | NullPointerException e) {
            view.printException(e.getMessage());
        } catch (RemoteException e) {
            view.printException(Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public void addAnswers(int questionId) throws RemoteException, NullPointerException {
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

    @Override
    public void displayQuestionsAndAnswers(int questionID) throws RemoteException, NullPointerException {
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