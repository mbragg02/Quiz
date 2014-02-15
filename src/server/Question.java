package server;

public class Question {

	private int questionID;
	private String question;
	private int correctAnswerID;

	public Question(int questionID, String quizQuestion) {
		setQuestionID(questionID);
		setQuestion(quizQuestion);
	}

	public int getQuestionID() {
		return questionID;
	}

	private void setQuestionID(int questionID) {
		this.questionID = questionID;
	}

	public String getQuestion() {
		return question;
	}

	private void setQuestion(String question) {
		this.question = question;
	}

	public int getCorrectAnswerID() {
		return correctAnswerID;
	}

	public void setCorrectAnswerID(int correctAnswerID) {
		this.correctAnswerID = correctAnswerID;
	}

}
