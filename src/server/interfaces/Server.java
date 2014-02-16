package server.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Server extends Remote {

	int createQuiz(String quizName) throws RemoteException, IllegalArgumentException, NullPointerException;

	int addQuestionToQuiz(int quizID, String quizQuestion) throws RemoteException, IllegalArgumentException, NullPointerException;

	void setQuizActive(int quizID) throws RemoteException;

	List<Quiz> getActiveQuizes() throws RemoteException;

	List<Game> setQuizInactive(int quizID) throws RemoteException, IllegalArgumentException, NullPointerException;

	int startGame(int quizID, String playerName) throws RemoteException, IllegalArgumentException, NullPointerException;

	void submitScore(int quizID, int gameID, int score) throws RemoteException, NullPointerException;

	void addAnswerToQuestion(int quizID, int questionID, String quizAnswer) throws RemoteException, IllegalArgumentException, NullPointerException;

	void setCorrectAnswer(int quizID, int questionID, int correctAnswer) throws RemoteException, NullPointerException;

	List<Question> getQuizQuestionsAndAnswers(int quizID) throws RemoteException, NullPointerException;

	List<String> getAnswersForQuestion(int quizID, int questionID) throws RemoteException, NullPointerException;

}