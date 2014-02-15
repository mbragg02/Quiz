package server;

public class Quiz {
	
	private int QuizID;
	private String quizName;
	private boolean active;
	
	public Quiz(int quizID, String quizName) {
		setQuizID(quizID);
		setQuizName(quizName);
		
	}

	public String getQuizName() {
		return quizName;
	}

	private void setQuizName(String quizName) {
		this.quizName = quizName;
	}

	public int getQuizID() {
		return QuizID;
	}

	private void setQuizID(int quizID) {
		QuizID = quizID;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	
	

}
