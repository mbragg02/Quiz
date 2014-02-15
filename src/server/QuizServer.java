package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizServer {
	
	private int quizIDs;
	private int questionIDs;

	private Map<Integer, Quiz> quizes;
	private Map<Quiz, List<Question>> questions;
	
	public QuizServer() {
		this.quizIDs = 0;
		quizes = new HashMap<Integer, Quiz>();
		questions = new HashMap<Quiz, List<Question>>();
	}

	public int createQuiz(String quizName) throws IllegalArgumentException, NullPointerException {
		
		if (quizName == null) {
			throw new NullPointerException("Quiz name was null");
		}
		
		if (quizName.isEmpty()) {
			throw new IllegalArgumentException("Quiz name can not be blank");
		}
		
		Quiz quiz = new Quiz(quizIDs, quizName);
		
		quizes.put(quiz.getQuizID(), quiz);
				
		questions.put(quiz, new ArrayList<Question>());
		
		++quizIDs;
		return quiz.getQuizID();
	}

	public int addQuestionToQuiz(int quizID, String quizQuestion) {
		
		if (quizQuestion.isEmpty()) {
			throw new IllegalArgumentException("Question can not be blank");
		}
		
		Question question = new Question(questionIDs, quizQuestion);
		
		questions.get(quizes.get(quizID)).add(question);		
		
		++questionIDs;
		return question.getQuestionID();
	}
	
	

}
