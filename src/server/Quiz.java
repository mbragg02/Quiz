package server;

public class Quiz {
	
	private int QuizID;
	private String quizName;
	
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
	
	

}
