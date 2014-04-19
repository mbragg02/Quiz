package server.factories;

import server.interfaces.Game;
import server.interfaces.Question;
import server.interfaces.Quiz;
import server.models.GameImpl;
import server.models.QuestionImpl;
import server.models.QuizImpl;
import server.utilities.QuizIdComparator;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * Factory to create quizzes, questions and games.
 *
 * @author Michael Bragg
 */
public class QuizFactory {

    private static QuizFactory quizFactory;

    private QuizFactory() {
        // Private empty constructor
    }

    public static QuizFactory getInstance() {
        if (quizFactory == null) {
            quizFactory = new QuizFactory();
        }
        return quizFactory;
    }

    public Quiz getQuiz(int id, String name) {
        return new QuizImpl(id, name);
    }

    public Question getQuestion(int id, String question) {
        return new QuestionImpl(id, question);
    }

    public Game getGame(int id, String playerName) {
        return new GameImpl(id, playerName);
    }

    public SimpleDateFormat getSimpleDataFormat(String dateFormat) {
        return new SimpleDateFormat(dateFormat);
    }

    public Date getDate() {
        return new Date();
    }

    public Comparator<Quiz> getQuizIdComparator() {
        return new QuizIdComparator();
    }
}
