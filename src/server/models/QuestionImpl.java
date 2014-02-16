package server.models;

import java.util.ArrayList;
import java.util.List;

import server.interfaces.Question;

public class QuestionImpl implements Question {

	private int questionID;
	private String question;
	private int correctAnswerID;
	private List<String> answers;

	public QuestionImpl(int questionID, String quizQuestion) {
		setQuestionID(questionID);
		setQuestion(quizQuestion);
		answers = new ArrayList<>();
	}

	@Override
	public int getQuestionID() {
		return questionID;
	}

	private void setQuestionID(int questionID) {
		this.questionID = questionID;
	}

	@Override
	public String getQuestion() {
		return question;
	}

	private void setQuestion(String question) {
		this.question = question;
	}

	@Override
	public int getCorrectAnswerID() {
		return correctAnswerID;
	}

	@Override
	public void setCorrectAnswerID(int correctAnswerID) {
		this.correctAnswerID = correctAnswerID;
	}
	
	@Override
	public void addAnswer(String answer) {
		answers.add(answer);
	}

	@Override
	public List<String> getAnswers() {
		return this.answers;
	}

}
