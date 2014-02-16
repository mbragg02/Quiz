package server.interfaces;

import java.util.List;

public interface Question {

	public abstract int getQuestionID();

	public abstract String getQuestion();

	public abstract int getCorrectAnswerID();

	public abstract void setCorrectAnswerID(int correctAnswerID);

	void addAnswer(String answer);

	List<String> getAnswers();

}