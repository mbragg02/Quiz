package server;

public class Answer {
	
	private int answerID;
	private String answer;

	public Answer(int answerID, String quizAnswer) {
		setAnswerID(answerID);
		setAnswer(quizAnswer);
	}

	public int getAnswerID() {
		return answerID;
	}

	private void setAnswerID(int questionID) {
		this.answerID = questionID;
	}

	public String getAnswer() {
		return answer;
	}

	private void setAnswer(String answer) {
		this.answer = answer;
	}

}
