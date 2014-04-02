package playerClient.interfaces;

import java.rmi.RemoteException;

/**
 * @author Michael Bragg
 *         Player client interface.
 */
public interface PlayerClient {

    /**
     * Launches the player client
     *
     * @throws RemoteException
     */
    void launch() throws RemoteException;

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
    void playQuiz() throws RemoteException;

    /**
     * Submits a player score.
     */
    void submitScore();
}