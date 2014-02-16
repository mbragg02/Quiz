package server.interfaces;

import java.util.List;
import java.util.Set;

public interface Quiz {

	public abstract String getQuizName();

	public abstract int getQuizID();

	public abstract boolean isActive();

	public abstract void setActive(boolean active);

	Question getQuestion(int questionId);

	List<Question> getQuestions();

	void addQuestion(Question quizQuestion);

}