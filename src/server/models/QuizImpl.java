package server.models;

import server.interfaces.Quiz;

public class QuizImpl implements Quiz {
	
	private int QuizID;
	private String quizName;
	private boolean active;
	
	public QuizImpl(int quizID, String quizName) {
		setQuizID(quizID);
		setQuizName(quizName);
		
	}

	@Override
	public String getQuizName() {
		return quizName;
	}

	private void setQuizName(String quizName) {
		this.quizName = quizName;
	}

	@Override
	public int getQuizID() {
		return QuizID;
	}

	private void setQuizID(int quizID) {
		QuizID = quizID;
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public void setActive(boolean active) {
		this.active = active;
	}

	
	

}
