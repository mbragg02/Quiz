package server.Factories;

import server.interfaces.Game;
import server.interfaces.Question;
import server.interfaces.Quiz;
import server.models.GameImpl;
import server.models.QuestionImpl;
import server.models.QuizImpl;

/**
 * Created by Michael Bragg
 * Factory to create quizzes, questions and games
 */
public class Factory {

    private static Factory fact;

    private Factory() {
        // Private empty constructor
    }

    public static Factory getInstance() {
        if (fact == null) {
            fact = new Factory();
        }
        return fact;
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

}
