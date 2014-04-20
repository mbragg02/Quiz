package models;

import factories.QuizFactory;
import interfaces.Game;
import interfaces.Question;
import interfaces.Quiz;
import interfaces.Server;
import utilities.DB;
import utilities.LoggerWrapper;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.util.*;
import java.util.logging.Level;


/**
 * The Quiz Server
 *
 * @author Michael Bragg
 */
public class ServerImpl extends UnicastRemoteObject implements Server {

    private static final long serialVersionUID = -8930948399199628273L;
    private static final int ZERO_INDEX = 0;

    private DateFormat dateFormat;
    private QuizFactory factory;
    private ServerData serverData;

    public ServerImpl(QuizFactory factory, ServerData serverData) throws RemoteException {
        super();
        this.factory = factory;
        this.serverData = serverData;
        dateFormat = factory.getSimpleDataFormat();
    }


    // Methods used by the setup client.

    @Override
    public int createQuiz(String quizName)
            throws IllegalArgumentException, NullPointerException {
        if (quizName == null) {
            throw new NullPointerException("Quiz name was null");
        }
        if (quizName.isEmpty()) {
            throw new IllegalArgumentException("Quiz name can not be blank");
        }

        Quiz quiz = factory.getQuiz(serverData.getQuizID(), quizName);
        serverData.addQuiz(quiz.getQuizID(), quiz);
        LoggerWrapper.log(Level.FINE, "Quiz \"" + quizName + "\" created.");
        return quiz.getQuizID();
    }

    @Override
    public int addQuestionToQuiz(int quizID, String quizQuestion)
            throws IllegalArgumentException, NullPointerException {

        if (quizQuestion.isEmpty()) {
            throw new IllegalArgumentException("Question can not be blank");
        }
        Question question = factory.getQuestion(serverData.getQuestionID(), quizQuestion);
        Quiz quiz = serverData.getQuiz(quizID);
        quiz.addQuestion(question);
        LoggerWrapper.log(Level.FINE, "Question added to quiz: \"" + quiz.getQuizName() + "\"");
        return question.getQuestionID();
    }

    @Override
    public void addAnswerToQuestion(int quizID, int questionID, String quizAnswer)
            throws IllegalArgumentException, NullPointerException {
        if (quizAnswer.isEmpty()) {
            throw new IllegalArgumentException("Answer can not be blank");
        }
        validateQuizAndQuestionID(quizID, questionID);

        Quiz quiz = serverData.getQuiz(quizID);
        quiz.getQuestion(questionID).addAnswer(quizAnswer);
        LoggerWrapper.log(Level.FINE, "Answer added to quiz: \"" + quiz.getQuizName() + "\"");
    }

    @Override
    public void setCorrectAnswer(int quizID, int questionID, int correctAnswer)
            throws NullPointerException, IllegalArgumentException, InputMismatchException {
        validateQuizAndQuestionID(quizID, questionID);

        Quiz quiz = serverData.getQuiz(quizID);
        Question question = quiz.getQuestion(questionID);

        int numberOfAnswers = question.getAnswers().size();
        // numberOfAnswers - 1 to get the highest answer index
        if (correctAnswer > numberOfAnswers - 1 || correctAnswer < 0) {
            throw new IllegalArgumentException("Not a valid answer");
        }
        LoggerWrapper.log(Level.FINE, "Answer set correct for Quiz: \"" + quiz.getQuizName() + "\"");

        question.setCorrectAnswerID(correctAnswer);
    }

    @Override
    public List<Question> getQuizQuestionsAndAnswers(int quizID) throws NullPointerException {
        return serverData.getQuiz(quizID).getQuestions();
    }

    @Override
    public List<String> getAnswersForQuestion(int quizID, int questionID) throws NullPointerException {
        validateQuizAndQuestionID(quizID, questionID);
        return serverData.getQuiz(quizID).getQuestion(questionID).getAnswers();
    }

    @Override
    public void setQuizActive(int quizID) {

        validateInactiveQuizzes(quizID);

        Quiz quiz = serverData.getQuiz(quizID);
        quiz.setActive(true);

        DB.write(serverData);

        LoggerWrapper.log(Level.FINE, "Quiz \"" + quiz.getQuizName() + "\" set ACTIVE");
    }

    @Override
    public List<Quiz> getActiveQuizzes() {
        List<Quiz> result = new ArrayList<>();

        for (Quiz quiz : serverData.getQuizzes().values()) {
            if (quiz.isActive()) {
                result.add(quiz);
            }
        }
        Collections.sort(result, factory.getQuizIdComparator());
        return result;
    }

    @Override
    public List<Quiz> getInactiveQuizzes() {
        List<Quiz> result = new ArrayList<>();

        for (Quiz quiz : serverData.getQuizzes().values()) {
            if (!quiz.isActive()) {
                result.add(quiz);
            }
        }
        Collections.sort(result, factory.getQuizIdComparator());
        return result;

    }

    @Override
    public List<Game> setQuizInactive(int quizID)
            throws IllegalArgumentException, NullPointerException {

        validateActiveQuizID(quizID);

        Quiz quiz = serverData.getQuiz(quizID);

        quiz.setActive(false);

        List<Game> playedGames = serverData.getGame(quizID);
        List<Game> result;

        if (playedGames == null) {
            result = new ArrayList<>();
        } else {
            result = getHighScoreGames(playedGames);
        }

        DB.write(serverData);

        LoggerWrapper.log(Level.FINE, "Quiz \"" + serverData.getQuiz(quizID).getQuizName() + "\" set INACTIVE");
        return result;
    }

    @Override
    public List<Game> getHighScoreGames(List<Game> gamesList) {
        int highScore = 0;
        List<Game> result = new ArrayList<>();

        // Calculate the highest score
        for (Game game : gamesList) {
            if (game.getScore() > highScore) {
                highScore = game.getScore();
            }
        }

        // Find the games with that high score
        for (Game game : gamesList) {
            if (game.getScore() == highScore) {
                result.add(game);
            }
        }
        LoggerWrapper.log(Level.FINE, "There were:" + result.size() + "games with a high score of: " + highScore);

        return result;
    }

    // Methods used by the player client.
    @Override
    public int startGame(int quizID, String playerName)
            throws IllegalArgumentException, NullPointerException {

        if (playerName.isEmpty()) {
            throw new IllegalArgumentException("Player name can not be blank");
        }
        if (Character.isDigit(playerName.charAt(ZERO_INDEX))) {
            throw new IllegalArgumentException("Name can not begin with a number");
        }
        validateActiveQuizID(quizID);

        Game game = factory.getGame(serverData.getGameID(), playerName);
        Quiz quiz = serverData.getQuiz(quizID);
        game.setNumberOfQuestions(quiz.getQuestions().size());

        List<Game> gamesList = serverData.getGame(quizID);

        if (gamesList == null) {
            gamesList = new ArrayList<>();
            serverData.addGame(quizID, gamesList);
        }

        gamesList.add(game);
        LoggerWrapper.log(Level.FINE, "\"" + playerName.toUpperCase() + "\" is playing quiz \"" + quiz.getQuizName() + "\"");

        return game.getGameID();
    }

    @Override
    public void submitScore(int quizID, int gameID, int score) throws NullPointerException {
        validateQuizAndGameID(quizID, gameID);
        List<Game> gamesList = serverData.getGame(quizID);

        for (Game game : gamesList) {
            if (game.getGameID() == gameID) {
                Date date = factory.getDate();
                String dateString = dateFormat.format(date);
                game.setPlayerScoreWithDate(score, dateString);
                break;
            }
        }
        LoggerWrapper.log(Level.FINE, "A score of: " + score + "has been submitted for Game:" + gameID + ", playing Quiz: " + quizID);

    }


    // Validations for Quiz, Question and Game IDs.

    /**
     * Validates if a qiven Quiz is active or not
     *
     * @param quizID int. The ID of the Quiz to check.
     * @throws NullPointerException If a Quiz does not exist or is INACTIVE for the given Quiz ID
     */
    private void validateActiveQuizID(int quizID) throws NullPointerException {
        Quiz quiz = serverData.getQuiz(quizID);
        if (!quiz.isActive()) {
            String warning = "Quiz " + quizID + ": " + "\"" + quiz.getQuizName() + "\"" + " is not currently active";
            LoggerWrapper.log(Level.SEVERE, warning);
            throw new NullPointerException(warning);
        }
    }

    private void validateInactiveQuizzes(int quizID) {
        Quiz quiz = serverData.getQuiz(quizID);
        if (quiz.isActive()) {
            String warning = "Quiz " + quizID + ": " + "\"" + quiz.getQuizName() + "\"" + " is already currently active";
            LoggerWrapper.log(Level.SEVERE, warning);
            throw new NullPointerException(warning);
        }
    }

    /**
     * Checks if the supplied Question & Quiz IDs are valid.
     *
     * @param quizID     int. The Quiz ID
     * @param questionID int. The Question ID
     * @throws NullPointerException If the Question or Quiz for the given IDs do not exist.
     */
    private void validateQuizAndQuestionID(int quizID, int questionID) throws NullPointerException {
        Quiz quiz = serverData.getQuiz(quizID);
        if (quiz.getQuestion(questionID) == null) {
            String warning = "Could not find a question with an id of: " + questionID;
            LoggerWrapper.log(Level.SEVERE, warning);
            throw new NullPointerException(warning);
        }
    }

    /**
     * Checks if the supplied Quiz and Game IDs are valid.
     *
     * @param quizID int. The Quiz ID
     * @param gameID int. The Game ID
     * @throws NullPointerException If the Quiz or Game do not exist. If there are no Games that have been played fpr the supplied Quiz.
     */
    private void validateQuizAndGameID(int quizID, int gameID) throws NullPointerException {
        validateActiveQuizID(quizID);

        List<Game> gamesList = serverData.getGame(quizID);
        String warning;
        if (gamesList == null) {
            warning = "Could not find any games for that quiz.";
            LoggerWrapper.log(Level.SEVERE, warning);
            throw new NullPointerException(warning);
        }
        Game result = null;

        for (Game game : gamesList) {
            if (game.getGameID() == gameID) {
                result = game;
            }
        }
        if (result == null) {
            warning = "Could not a game with that game ID.";
            LoggerWrapper.log(Level.SEVERE, warning);
            throw new NullPointerException("Could not a game with that game ID.");
        }
    }
}