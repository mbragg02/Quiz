package server.models;

import server.interfaces.Question;
import server.interfaces.Quiz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuizImpl implements Quiz, Serializable {
	
	private static final long serialVersionUID = 2700824645029004884L;
	private int QuizID;
	private String quizName;
	private boolean active;
	private List<Question> questions;
	
	public QuizImpl(int quizID, String quizName) {
		setQuizID(quizID);
		setQuizName(quizName);
		questions = new ArrayList<>();
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
	
	@Override
	public Question getQuestion(int questionId) {
		
		for (Question question : questions) {
			if(question.getQuestionID() == questionId)
				return question;
		}
		return null;
	}
	
	@Override
	public List<Question> getQuestions() {
		return this.questions;
	}
		
	@Override
	public void addQuestion(Question quizQuestion) {
		questions.add(quizQuestion);
	}

	
	

}
