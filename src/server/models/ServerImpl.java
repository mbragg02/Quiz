package server.models;

import server.Factories.QuizFactory;
import server.interfaces.Game;
import server.interfaces.Question;
import server.interfaces.Quiz;
import server.interfaces.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Michael Bragg
 * The Quiz server.
 */
public class ServerImpl extends UnicastRemoteObject implements Server {

    private static final long serialVersionUID = -8930948399199628273L;
    private static final String DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
    private static final int ZERO_INDEX = 0;

    private DateFormat dateFormat;
    private Logger logger;
    private QuizFactory factory;
    private ServerData serverData;

    public ServerImpl(QuizFactory factory,Logger logger, ServerData serverData) throws RemoteException {
        super();
        this.factory = factory;
        this.logger = logger;
        this.serverData = serverData;
        dateFormat = factory.getSimpleDataFormat(DATE_FORMAT);
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
        logger.fine("Quiz \"" + quizName + "\" created.");
        return quiz.getQuizID();
    }

    @Override
    public int addQuestionToQuiz(int quizID, String quizQuestion)
            throws IllegalArgumentException, NullPointerException {

        if (quizQuestion.isEmpty()) {
            throw new IllegalArgumentException("Question can not be blank");
        }
        Question question = factory.getQuestion(serverData.getQuestionID(), quizQuestion);
        serverData.getQuiz(quizID).addQuestion(question);
        logger.fine("Question added to quiz: \"" + serverData.getQuiz(quizID).getQuizName() + "\"");
        return question.getQuestionID();
    }

    @Override
    public void addAnswerToQuestion(int quizID, int questionID, String quizAnswer)
            throws IllegalArgumentException, NullPointerException {
        validateQuizAndQuestionID(quizID, questionID);
        if (quizAnswer.isEmpty()) {
            throw new IllegalArgumentException("Answer can not be blank");
        }
        serverData.getQuiz(quizID).getQuestion(questionID).addAnswer(quizAnswer);
        logger.fine("Answer added to quiz: \"" + serverData.getQuiz(quizID).getQuizName() + "\"");
    }

    @Override
    public void setCorrectAnswer(int quizID, int questionID, int correctAnswer) throws NullPointerException {
        validateQuizAndQuestionID(quizID, questionID);
        int numberOfAnswers = serverData.getQuiz(quizID).getQuestion(questionID).getAnswers().size();
        // numberOfAnswers - 1 to get the highest answer index
        if (correctAnswer > numberOfAnswers - 1 || correctAnswer < 0) {
            throw new IllegalArgumentException("Not a valid answer");
        }
        serverData.getQuiz(quizID).getQuestion(questionID).setCorrectAnswerID(correctAnswer);
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
        serverData.getQuiz(quizID).setActive(true);
        logger.fine("Quiz \"" + serverData.getQuiz(quizID).getQuizName() + "\" set ACTIVE");
    }

    @Override
    public List<Quiz> getActiveQuizzes() {
        List<Quiz> activeQuizzes = new ArrayList<>();

        for (Quiz quiz : serverData.getQuizzes().values()) {
            if (quiz.isActive()) {
                activeQuizzes.add(quiz);
            }
        }
        return activeQuizzes;
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
        logger.fine("Quiz \"" + serverData.getQuiz(quizID).getQuizName() + "\" set INACTIVE");

        return result;
    }

    private List<Game> getHighScoreGames(List<Game> gamesList) {
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
        return result;
    }

    // Methods used by the player client.
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

        Game game = factory.getGame(serverData.getGameID(), playerName);
        Quiz quiz = serverData.getQuiz(quizID);
        game.setNumberOfQuestions(quiz.getQuestions().size());

        List<Game> gamesList = serverData.getGame(quizID);

        if (gamesList == null) {
            gamesList = new ArrayList<>();
            serverData.addGame(quizID, gamesList);
        }

        gamesList.add(game);
        logger.fine("\"" + playerName.toUpperCase() + "\" is playing quiz \"" + quiz.getQuizName() + "\"");
        return game.getGameID();
    }

    @Override
    public void submitScore(int quizID, int gameID, int score) throws NullPointerException {
        validateQuizAndGameID(quizID, gameID);
        List<Game> gamesList = serverData.getGame(quizID);

        for (Game game : gamesList) {
            if (game.getGameID() == gameID) {
                Date date = factory.getDate();
                game.setPlayerScoreWithDate(score, dateFormat.format(date));
                break;
            }
        }
    }

    // Validations of Quiz, Questions and Games.
    private void validateActiveQuizID(int quizID) throws NullPointerException {
        Quiz quiz = serverData.getQuiz(quizID);
        if (!quiz.isActive()) {
            throw new IllegalArgumentException("Quiz not active");
        }
    }

    private void validateQuizAndQuestionID(int quizID, int questionID) throws NullPointerException {
        Quiz quiz = serverData.getQuiz(quizID);
        if (quiz.getQuestion(questionID) == null) {
            throw new NullPointerException("Could not find a question with that ID");
        }
    }

    private void validateQuizAndGameID(int quizID, int gameID) throws NullPointerException {
        validateActiveQuizID(quizID);

        List<Game> gamesList = serverData.getGame(quizID);

//        if (gamesList == null) {
//            throw new NullPointerException("Could not find any games for that quiz.");
//        }
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