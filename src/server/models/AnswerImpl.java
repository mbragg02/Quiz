package server.models;

import server.interfaces.Answer;

public class AnswerImpl implements Answer {
	
	private int answerID;
	private String answer;

	public AnswerImpl(int answerID, String quizAnswer) {
		setAnswerID(answerID);
		setAnswer(quizAnswer);
	}

	@Override
	public int getAnswerID() {
		return answerID;
	}

	private void setAnswerID(int questionID) {
		this.answerID = questionID;
	}

	@Override
	public String getAnswer() {
		return answer;
	}

	private void setAnswer(String answer) {
		this.answer = answer;
	}

}
