package setupClient.controllers;

import java.rmi.RemoteException;

/**
 * Methods for creating a new Quiz
 *
 * @author Michael Bragg
 */
public interface CreateQuizController {

    /**
     * Calls the server to create a new quiz, with a name from the user.
     *
     * @throws RemoteException
     */
    void createQuizWithName() throws RemoteException;

    /**
     * Gets a Question from the user and calls the server to add it to the quiz.
     *
     * @throws RemoteException
     */
    void addQuestion() throws RemoteException;

    /**
     * Gets an answer for the Question and calls the server to add it to the Question.
     *
     * @param questionId int. The question ID
     * @throws RemoteException
     * @throws NullPointerException
     */
    void addAnswers(int questionId) throws RemoteException, NullPointerException;

    /**
     * Asks the user to select the correct answer and sets it on the server.
     *
     * @param questionId int. The question ID
     * @throws RemoteException
     * @throws NullPointerException
     */
    void selectCorrectAnswer(int questionId) throws RemoteException, NullPointerException;

    /**
     * Logic for displaying a question with all it's answers. Calls the view to do the printing.
     *
     * @param questionID int. The question ID
     * @throws RemoteException
     * @throws NullPointerException
     */
    void displayQuestionsAndAnswers(int questionID) throws RemoteException, NullPointerException;

    /**
     * Asks the user to set the Quiz as being either ACTIVE or INACTIVE, and updates the server.
     *
     * @throws RemoteException
     */
    void setQuizStatus() throws RemoteException;
}
