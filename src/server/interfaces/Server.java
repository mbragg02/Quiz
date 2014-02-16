package server.interfaces;

import java.util.List;
import java.util.Map;

public interface Server {

	public abstract int createQuiz(String quizName)
			throws IllegalArgumentException, NullPointerException;

	public abstract int addQuestionToQuiz(int quizID, String quizQuestion)
			throws IllegalArgumentException, NullPointerException;

	public abstract void setQuizActive(int quizID);

	public abstract List<Quiz> getActiveQuizes();

	public abstract List<Game> setQuizInactive(int quizID)
			throws IllegalArgumentException, NullPointerException;

	public abstract int startGame(int quizID, String playerName)
			throws IllegalArgumentException, NullPointerException;

	public abstract void submitScore(int quizID, int gameID, int score)
			throws NullPointerException;

	public abstract void launch();

	void addAnswerToQuestion(int quizID, int questionID, String quizAnswer)
			throws IllegalArgumentException, NullPointerException;

	void setCorrectAnswer(int quizID, int questionID, int correctAnswer)
			throws NullPointerException;

	List<Question> getQuizQuestionsAndAnswers(int quizID)
			throws NullPointerException;

	List<String> getAnswersForQuestion(int quizID, int questionID)
			throws NullPointerException;

}