package server.interfaces;

public interface Question {

	public abstract int getQuestionID();

	public abstract String getQuestion();

	public abstract int getCorrectAnswerID();

	public abstract void setCorrectAnswerID(int correctAnswerID);

}