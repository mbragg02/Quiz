package server.models;

import server.Factories.Factory;
import server.Factories.IdFactory;
import server.interfaces.Game;
import server.interfaces.Question;
import server.interfaces.Quiz;
import server.interfaces.Server;
import server.utilities.ShutdownHook;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * @author Michael Bragg
 * The Quiz server.
 */
public class ServerImpl extends UnicastRemoteObject implements Server {

    private static final long serialVersionUID = -8930948399199628273L;
    private static final String LOG_FILENAME = "server.log";
    private static final String DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
    private static final int ZERO_INDEX = 0;

    private DateFormat dateFormat;
    private Logger logger;
    private IdFactory idFact;
    private Factory fact;
    private Map<Integer, Quiz> quizes;
    private Map<Integer, List<Game>> games;

    public ServerImpl(Factory fact, IdFactory idFact) throws RemoteException {
        super();
        this.idFact = idFact;
        this.fact = fact;
        dateFormat = new SimpleDateFormat(DATE_FORMAT);
        quizes = new HashMap<>();
        games = new HashMap<>();
        serverLogInitialize();
    }

    private void serverLogInitialize() {
        logger = Logger.getLogger("QuizServerImplLogger");
        FileHandler fileHandler;

        try {
            fileHandler = new FileHandler(LOG_FILENAME, true);
            logger.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
        } catch (SecurityException | IOException e) {
            logger.severe(e.toString());
        }
        logger.info("Server Started");

        Runtime.getRuntime().addShutdownHook(new ShutdownHook(logger));
    }

    @Override
    public int createQuiz(String quizName)
            throws IllegalArgumentException, NullPointerException {

        if (quizName == null) {
            throw new NullPointerException("Quiz name was null");
        }

        if (quizName.isEmpty()) {
            throw new IllegalArgumentException("Quiz name can not be blank");
        }

        Quiz quiz = fact.getQuiz(idFact.getQuizID(), quizName);
        quizes.put(quiz.getQuizID(), quiz);
        logger.info("Quiz \"" + quizName + "\" created.");
        return quiz.getQuizID();
    }

    @Override
    public int addQuestionToQuiz(int quizID, String quizQuestion)
            throws IllegalArgumentException, NullPointerException {

        if (quizQuestion.isEmpty()) {
            throw new IllegalArgumentException("Question can not be blank");
        }

        validateQuizID(quizID);
        Question question = fact.getQuestion(idFact.getQuestionID(), quizQuestion);
        quizes.get(quizID).addQuestion(question);
        logger.info("Question added to quiz: \"" + quizes.get(quizID).getQuizName() + "\"");
        return question.getQuestionID();
    }

    @Override
    public void addAnswerToQuestion(int quizID, int questionID, String quizAnswer)
            throws IllegalArgumentException, NullPointerException {
        validateQuizAndQuestionID(quizID, questionID);
        if (quizAnswer.isEmpty()) {
            throw new IllegalArgumentException("Answer can not be blank");
        }
        quizes.get(quizID).getQuestion(questionID).addAnswer(quizAnswer);
        logger.info("Answer added to quiz: \"" + quizes.get(quizID).getQuizName() + "\"");
    }

    @Override
    public void setCorrectAnswer(int quizID, int questionID, int correctAnswer) throws NullPointerException {
        validateQuizAndQuestionID(quizID, questionID);
        int numberOfAnswers = quizes.get(quizID).getQuestion(questionID).getAnswers().size();

        // numberOfAnswers - 1 to get the highest answer index
        if (correctAnswer > numberOfAnswers - 1 || correctAnswer < 0) {
            throw new IllegalArgumentException("Not a valid answer");
        }
        quizes.get(quizID).getQuestion(questionID).setCorrectAnswerID(correctAnswer);
    }

    @Override
    public List<Question> getQuizQuestionsAndAnswers(int quizID) throws NullPointerException {
        validateQuizID(quizID);
        return quizes.get(quizID).getQuestions();
    }

    @Override
    public List<String> getAnswersForQuestion(int quizID, int questionID) throws NullPointerException {
        validateQuizAndQuestionID(quizID, questionID);
        return quizes.get(quizID).getQuestion(questionID).getAnswers();
    }

    @Override
    public void setQuizActive(int quizID) {
        validateQuizID(quizID);
        quizes.get(quizID).setActive(true);
        logger.info("Quiz \"" + quizes.get(quizID).getQuizName() + "\" set ACTIVE");
    }

    @Override
    public List<Quiz> getActiveQuizes() {
        List<Quiz> activeQuizes = new ArrayList<>();
        for (Quiz quiz : quizes.values()) {
            if (quiz.isActive()) {
                activeQuizes.add(quiz);
            }
        }
        return activeQuizes;
    }

    @Override
    public List<Game> setQuizInactive(int quizID)
            throws IllegalArgumentException, NullPointerException {

        validateActiveQuizID(quizID);

        Quiz quiz = quizes.get(quizID);

        quiz.setActive(false);

        List<Game> playedGames = games.get(quizID);
        List<Game> result;

        if (playedGames == null) {
            result = new ArrayList<>();
        } else {
            result = getHighscoreGames(playedGames);
        }
        logger.info("Quiz \"" + quizes.get(quizID).getQuizName() + "\" set INACTIVE");

        return result;
    }

    private List<Game> getHighscoreGames(List<Game> gamesList) {
        int highscore = 0;
        List<Game> result = new ArrayList<>();

        // Calculate the highest score
        for (Game game : gamesList) {
            if (game.getScore() > highscore) {
                highscore = game.getScore();
            }
        }
        // Find the games with that high score
        for (Game game : gamesList) {
            if (game.getScore() == highscore) {
                result.add(game);
            }
        }
        return result;
    }

    @Override
    public int startGame(int quizID, String playerName)
            throws IllegalArgumentException, NullPointerException {
        validateActiveQuizID(quizID);

        if (playerName.isEmpty()) {
            throw new IllegalArgumentException("Player name can not be blank");
        }
        if (Character.isDigit(playerName.charAt(ZERO_INDEX))) {
            throw new IllegalArgumentException("Name can not begin with a number");
        }

        Game game = fact.getGame(idFact.getGameID(), playerName);
        Quiz quiz = quizes.get(quizID);
        game.setNumberOfQuestions(quiz.getQuestions().size());

        List<Game> gamesList = games.get(quizID);

        if (gamesList == null) {
            gamesList = new ArrayList<>();
            games.put(quizID, gamesList);
        }

        gamesList.add(game);
        logger.info("\"" + playerName.toUpperCase() + "\" is playing quiz \"" + quiz.getQuizName() + "\"");
        return game.getGameID();
    }

    @Override
    public void submitScore(int quizID, int gameID, int score) throws NullPointerException {
        validateQuizAndGameID(quizID, gameID);
        List<Game> gamesList = games.get(quizID);

        for (Game game : gamesList) {
            if (game.getGameID() == gameID) {
                Date date = new Date();
                game.setPlayerScoreWithDate(score, dateFormat.format(date));
                break;
            }
        }
    }

    private void validateQuizID(int quizID) throws NullPointerException {
        if (!quizes.containsKey(quizID)) {
            throw new NullPointerException("Could not find a quiz with the ID of: " + quizID);
        }
    }

    private void validateActiveQuizID(int quizID) throws NullPointerException {
        Quiz quiz = quizes.get(quizID);
        validateQuizID(quizID);
        if (!quiz.isActive()) {
            throw new IllegalArgumentException("Quiz not active");
        }
    }

    private void validateQuizAndQuestionID(int quizID, int questionID) throws NullPointerException {
        validateQuizID(quizID);
        Quiz quiz = quizes.get(quizID);
        if (quiz.getQuestion(questionID) == null) {
            throw new NullPointerException("Could not find a question with that ID");
        }
    }

    private void validateQuizAndGameID(int quizID, int gameID) {
        validateActiveQuizID(quizID);
        List<Game> gamesList = games.get(quizID);

        if (gamesList == null) {
            throw new NullPointerException("Could not find any games for that quiz.");
        }
        Game result = null;

        for (Game game : gamesList) {
            if (game.getGameID() == gameID) {
                result = game;
            }
        }
        if (result == null) {
            throw new NullPointerException("Could not a game with that game ID.");
        }
    }
}