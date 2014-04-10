package server.interfaces;

/**
 * A Game is created with a players name and a unique game ID.
 *
 * @author Michael Bragg
 */
public interface Game {

    /**
     * Get the games unique ID.
     *
     * @return int. Game ID
     */
    int getGameID();

    /**
     * Get the players name for the game.
     *
     * @return String. A players name
     */
    String getPlayerName();

    /**
     * Get the games score.
     *
     * @return int. A score.
     */
    int getScore();

    /**
     * Set a score for the game.
     *
     * @param score int. The score
     * @param date  String. The date of submission.
     */
    void setPlayerScoreWithDate(int score, String date);

    /**
     * Get the date on which the game was completed.
     *
     * @return String. Completion date/
     */
    String getDateCompleted();

    /**
     * Get the number of questions for this game.
     *
     * @return int. Number of questions
     */
    int getNumberOfQuestions();

    /**
     * Set the number of questions for this game.
     *
     * @param numberOfQuestions int. Number of questions
     */
    void setNumberOfQuestions(int numberOfQuestions);

}