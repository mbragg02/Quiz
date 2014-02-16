package server.interfaces;

import java.util.List;

public interface Quiz {

	String getQuizName();

	int getQuizID();

	boolean isActive();

	void setActive(boolean active);

	Question getQuestion(int questionId);

	List<Question> getQuestions();

	void addQuestion(Question quizQuestion);

}