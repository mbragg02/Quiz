package playerClient.controllers;

import playerClient.interfaces.PlayerClient;
import playerClient.views.PlayerClientView;
import server.interfaces.Question;
import server.interfaces.Quiz;
import server.interfaces.Server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author Michael Bragg
 *         Class for a player client.
 */
public class PlayerClientImpl implements PlayerClient {

    private final Server model;
    private final PlayerClientView view;
    private List<Question> questions;
    private int score;
    private int answer;
    private int quizID;
    private int gameID;
    private String playerName;

    public PlayerClientImpl(Server model, PlayerClientView view) {
        this.model = model;
        this.view = view;
        questions = new ArrayList<>();
        score = 0;
        answer = 0;
        quizID = 0;
        gameID = 0;
    }

    @Override
    public void launch() throws RemoteException {
        view.displayWelcomeMessage();

        if (model.getActiveQuizzes().isEmpty()) {
            view.displayNoActiveQuizesMessage();
        } else {
            setPlayerName();
            displayActiveQuizzes();
            selectQuizToPlay();
            playGame();
            displayQuestions();
            submitScore();
        }
    }

    @Override
    public void setPlayerName() {
        view.displayNameRequest();
        try {
            this.playerName = view.getNextLineFromConsole();
        } catch (NoSuchElementException ex) {
            view.displayException(ex.getMessage());
        }
    }

    @Override
    public void displayActiveQuizzes() throws RemoteException {
        List<Quiz> quizzes = model.getActiveQuizzes();
        view.displayCurrentActiveQuizesMessage();
        for (Quiz quiz : quizzes) {
            view.displayQuizDetails(quiz.getQuizID(), quiz.getQuizName());
        }
    }

    private void selectQuizToPlay() {
        do {
            try {
                view.displaySelectQuizMessage();
                this.quizID = view.getNextIntFromConsole();
                break;
            } catch (InputMismatchException ex) {
                view.displayException(ex.getMessage());
            }
        } while (true);
        view.getNextLineFromConsole();
    }

    @Override
    public void playGame() throws RemoteException {
        do {
            try {
                this.gameID = model.startGame(this.quizID, this.playerName);
                break;
            } catch (RemoteException ex) {
                view.displayException(ex.getMessage());
            } catch (IllegalArgumentException ex) {
                // Player name can not be blank
                view.displayException(ex.getMessage());
                setPlayerName();
            } catch (NullPointerException ex) {
                // Invlaid or inactive quiz ID
                view.displayException(ex.getMessage());
                displayActiveQuizzes();
                selectQuizToPlay();
            }
        } while (true);


    }

    @Override
    public void displayQuestions() {

        try {
            questions = model.getQuizQuestionsAndAnswers(this.quizID);
        } catch (NullPointerException | RemoteException ex) {
            view.displayException(ex.getMessage());
        }

        int questionCount = 1;
        for (Question question : questions) {
            view.displayQuestion(questionCount, question.getQuestion());
            displayAnswers(question);
            selectAnswer();
            if (answer == question.getCorrectAnswerID()) {
                ++score;
            }
            ++questionCount;
        }
    }

    private void displayAnswers(Question question) {
        int answerCount = 1;
        for (String answer : question.getAnswers()) {
            view.displayAnswer(answerCount, answer);
            ++answerCount;
        }
    }

    @Override
    public void selectAnswer() {
        int playerAnswer;
        do {
            view.displayAnswerRequest();
            try {
                playerAnswer = view.getNextIntFromConsole();
                break;
            } catch (InputMismatchException ex) {
                view.displayInvlaidInputException();
                view.getNextLineFromConsole();
            }
        } while (true);

        // -1 because answers stored with a zero index. User interface starts an 1.
        this.answer = playerAnswer - 1;
    }

    @Override
    public void submitScore() {
        try {
            model.submitScore(this.quizID, this.gameID, this.score);
        } catch (NullPointerException | RemoteException e) {
            view.displayException(e.getMessage());
        }
        view.displayScoreDetails(score, questions.size());
        view.displayExitMessage(playerName);
    }


}