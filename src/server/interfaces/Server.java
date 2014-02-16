package server.interfaces;

import java.util.List;

public interface Server {

	int createQuiz(String quizName) throws IllegalArgumentException, NullPointerException;

	int addQuestionToQuiz(int quizID, String quizQuestion) throws IllegalArgumentException, NullPointerException;

	void setQuizActive(int quizID);

	List<Quiz> getActiveQuizes();

	List<Game> setQuizInactive(int quizID) throws IllegalArgumentException, NullPointerException;

	int startGame(int quizID, String playerName) throws IllegalArgumentException, NullPointerException;

	void submitScore(int quizID, int gameID, int score) throws NullPointerException;

	void launch();

	void addAnswerToQuestion(int quizID, int questionID, String quizAnswer) throws IllegalArgumentException, NullPointerException;

	void setCorrectAnswer(int quizID, int questionID, int correctAnswer) throws NullPointerException;

	List<Question> getQuizQuestionsAndAnswers(int quizID) throws NullPointerException;

	List<String> getAnswersForQuestion(int quizID, int questionID) throws NullPointerException;

}