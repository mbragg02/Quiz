package server;

public class QuizServer {
	
	
	private int quizIDs;
	
	public QuizServer() {
		this.quizIDs = 0;
	}

	public int createQuiz(String quizName) throws IllegalArgumentException, NullPointerException {
		
		if (quizName == null) {
			throw new NullPointerException("Quiz name was null");
		}
		
		if (quizName.isEmpty()) {
			throw new IllegalArgumentException("Quiz name can not be blank");
		}
		
		Quiz quiz = new Quiz(quizIDs, quizName);
		
		++quizIDs;
		
		return quiz.getQuizID();
	}
	
	

}
