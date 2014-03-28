package server.Factories;

/**
 * Created by Michael Bragg
 * Factory to create and manage IDs for quizzes, questions and games.
 */
public class IDFactory {

    private int quizIDs;
    private int questionIDs;
    private int gameIDs;

    private static IDFactory factory;

    private IDFactory() {
        initializeIDs();
    }

    public static IDFactory getInstance() {
        if (factory == null) {
            factory = new IDFactory();
        }
        return factory;
    }

    void initializeIDs() {
        this.quizIDs = 0;
        this.questionIDs = 0;
        this.gameIDs = 0;
    }

    public int getQuizID() {
        int id = quizIDs;
        ++quizIDs;
        return id;
    }

   public int getQuestionID() {
        int id = questionIDs;
        ++questionIDs;
        return id;
    }

    public int getGameID() {
        int id = gameIDs;
        ++gameIDs;
        return id;
    }
}