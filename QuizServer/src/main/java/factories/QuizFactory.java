package factories;

import interfaces.Game;
import interfaces.Question;
import interfaces.Quiz;
import models.GameImpl;
import models.QuestionImpl;
import models.QuizImpl;
import utilities.QuizIdComparator;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * Factory to create quizzes, questions and games.
 *
 * @author Michael Bragg
 */
public class QuizFactory {

    private static final String DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";


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

    public SimpleDateFormat getSimpleDataFormat() {
        return new SimpleDateFormat(DATE_FORMAT);
    }

    public Date getDate() {
        return new Date();
    }

    public Comparator<Quiz> getQuizIdComparator() {
        return new QuizIdComparator();
    }
}
