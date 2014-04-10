package playerClient.interfaces;

import java.rmi.RemoteException;

/**
 * Player client interface.
 *
 * @author Michael Bragg
 */
public interface PlayerClient {

    /**
     * Launches the player client
     *
     * @throws RemoteException
     */
    void launch() throws RemoteException;

    void setPlayerName();

    /**
     * Displays the current active/playable quizzes
     *
     * @throws RemoteException
     */
    void displayActiveQuizzes() throws RemoteException;

    /**
     * Plays a Quiz
     *
     * @throws RemoteException
     */
    void playGame() throws RemoteException;

    void displayQuestions();

    void selectAnswer();

    /**
     * Submits a player score.
     */
    void submitScore();
}