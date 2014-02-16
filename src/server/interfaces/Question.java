package server.interfaces;

import java.util.List;

public interface Question {

	int getQuestionID();

	String getQuestion();

	int getCorrectAnswerID();

	void setCorrectAnswerID(int correctAnswerID);

	void addAnswer(String answer);

	List<String> getAnswers();

}