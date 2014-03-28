package setupClient.interfaces;

import java.rmi.RemoteException;

/**
 * @author Michael Bragg
 * Interfaces for methods relating to creating a new Quiz.
 */
public interface CreateQuiz {
    /**
     * Method to allow a user to create a new quiz with a name.
     * @throws RemoteException
     * @throws NullPointerException
     */
    void createQuizWithName() throws RemoteException, NullPointerException;

    /**
     * Method to allow a user to add questions & answers to a Quiz.
     * @throws RemoteException
     */
    void addQuestionsAndAnswers() throws RemoteException;

    /**
     * Method to allow a user to choose an answer as being correct.
     * @param questionId int. question Id
     * @throws RemoteException
     * @throws NullPointerException
     */
    void selectCorrectAnswer(int questionId) throws RemoteException, NullPointerException;

    /**
     * Method to set a quiz as active or inactive.
     * @throws RemoteException
     */
    void setQuizStatus() throws RemoteException;
}
