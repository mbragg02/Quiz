package server.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * The methods required for the quiz server.
 *
 * @author Michael Bragg
 */
public interface Server extends Remote {
    /**
     * Create a new quiz with a name
     *
     * @param quizName String. The name for the Quiz.
     * @return int. The quiz ID.
     * @throws RemoteException
     * @throws IllegalArgumentException
     * @throws NullPointerException
     */
    int createQuiz(String quizName) throws RemoteException, IllegalArgumentException, NullPointerException;

    /**
     * Add a new Quiestion to a Quiz
     *
     * @param quizID       int The ID of the Quiz to add the question to.
     * @param quizQuestion String. The question
     * @return int. The question ID.
     * @throws RemoteException
     * @throws IllegalArgumentException
     * @throws NullPointerException
     */
    int addQuestionToQuiz(int quizID, String quizQuestion) throws RemoteException, IllegalArgumentException, NullPointerException;

    /**
     * Set a Quiz to ACTIVE. i.e Playable by a player client
     *
     * @param quizID int The Quiz ID to set ACTIVE
     * @throws RemoteException if the quiz ID is not valid
     */
    void setQuizActive(int quizID) throws RemoteException;

    /**
     * Get a list of all the ACTIVE quizzes
     *
     * @return List<Quiz>
     * @throws RemoteException
     */
    List<Quiz> getActiveQuizzes() throws RemoteException;

    /**
     * Get a list of all the INACTIVE quizzes
     *
     * @return List<Quiz>
     * @throws RemoteException
     */
    List<Quiz> getInactiveQuizzes() throws RemoteException;

    /**
     * Set a particular Quiz as INACTIVE. i.e No longer playable.
     *
     * @param quizID int. The ID of the Quiz to set INACTIVE
     * @return List<Game> A list of games who have the top score.
     * @throws RemoteException
     * @throws IllegalArgumentException
     * @throws NullPointerException
     */
    List<Game> setQuizInactive(int quizID) throws RemoteException, IllegalArgumentException, NullPointerException;

    List<Game> getHighScoreGames(List<Game> gamesList) throws RemoteException;

    /**
     * Begin a new game playing a particular Quiz
     *
     * @param quizID     int. The iD of the Quiz to play
     * @param playerName String. The name of the player.
     * @return int. The game ID.
     * @throws RemoteException
     * @throws IllegalArgumentException
     * @throws NullPointerException
     */
    int startGame(int quizID, String playerName) throws RemoteException, IllegalArgumentException, NullPointerException;

    /**
     * Submit a score once a game has finished
     *
     * @param quizID int. The ID of the Quiz that was being player.
     * @param gameID int. The ID of the the particular game.
     * @param score  int. The score for that game.
     * @throws RemoteException
     * @throws NullPointerException
     */
    void submitScore(int quizID, int gameID, int score) throws RemoteException, NullPointerException;

    /**
     * Add an answer to a question (in a Quiz)
     *
     * @param quizID     int. The ID of the Quiz (that the question is in that the answer is for)
     * @param questionID int. The ID of the question that the answer is for.
     * @param quizAnswer String. The answer to the question.
     * @throws RemoteException
     * @throws IllegalArgumentException
     * @throws NullPointerException
     */
    void addAnswerToQuestion(int quizID, int questionID, String quizAnswer) throws RemoteException, IllegalArgumentException, NullPointerException;

    /**
     * Set's a particular answer as being correct.
     *
     * @param quizID        int. The ID of the Quiz that contains the question/answer
     * @param questionID    int. The ID of the particular Question.
     * @param correctAnswer int. The number of the correct answer
     * @throws RemoteException
     * @throws NullPointerException
     */
    void setCorrectAnswer(int quizID, int questionID, int correctAnswer) throws RemoteException, NullPointerException;

    /**
     * Get a list of Questions (and containging answers) for a Quiz
     *
     * @param quizID int. The ID of the Quiz that contains the Questions/Answers
     * @return List<Question> The list of Questions.
     * @throws RemoteException
     * @throws NullPointerException
     */
    List<Question> getQuizQuestionsAndAnswers(int quizID) throws RemoteException, NullPointerException;

    /**
     * Get a list of all the answers for a particular Question.
     *
     * @param quizID     int. The Id of the Quiz that contains the particular Question.
     * @param questionID int. The ID of the Question for the requuied list of Answers.
     * @return List<String> The list of Answers.
     * @throws RemoteException
     * @throws NullPointerException
     */
    List<String> getAnswersForQuestion(int quizID, int questionID) throws RemoteException, NullPointerException;

}