package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizServer {
	
	private int quizIDs;
	private int questionIDs;
	private int answerIDs;

	private Map<Integer, Quiz> quizes;
	private Map<Integer, Question> questions;
	private Map<Quiz, List<Question>> quizQuestions;
	private Map<Question, List<Answer>> questionAnswers;
	
	public QuizServer() {
		this.quizIDs = 0;
		quizes   	    = new HashMap<Integer, Quiz>();
		questions 		= new HashMap<Integer, Question>();
		quizQuestions   = new HashMap<Quiz, List<Question>>();
		questionAnswers = new HashMap<Question, List<Answer>>();
	}

	public int createQuiz(String quizName) 
			throws IllegalArgumentException, NullPointerException {
		
		if (quizName == null) {
			throw new NullPointerException("Quiz name was null");
		}
		
		if (quizName.isEmpty()) {
			throw new IllegalArgumentException("Quiz name can not be blank");
		}
		
		Quiz quiz = new Quiz(quizIDs, quizName);
		
		quizes.put(quiz.getQuizID(), quiz);
		
		if(quizQuestions.get(quiz) == null) {
			quizQuestions.put(quiz, new ArrayList<Question>());
		}
		
		++quizIDs;
		return quiz.getQuizID();
	}

	public int addQuestionToQuiz(int quizID, String quizQuestion) 
			throws IllegalArgumentException, NullPointerException {
		
		if (quizQuestion.isEmpty()) {
			throw new IllegalArgumentException("Question can not be blank");
		}
		
		validateQuizID(quizID);
		
		Question question = new Question(questionIDs, quizQuestion);
		
		questions.put(questionIDs, question);
		
		quizQuestions.get(quizes.get(quizID)).add(question);
		
		questionAnswers.put(question, new ArrayList<Answer>());
		
		++questionIDs;
		return question.getQuestionID();
	}

	public int addAnswerToQuestion(int questionID, String quizAnswer) 
			throws IllegalArgumentException, NullPointerException {
		
		if (quizAnswer.isEmpty()) {
			throw new IllegalArgumentException("Answer can not be blank");
		}
		validateQuestionID(questionID);
		
		Answer answer = new Answer(answerIDs, quizAnswer);
		
		questionAnswers.get(questions.get(questionID)).add(answer);
		++answerIDs;
		return answer.getAnswerID();
	}

	public void setCorrectAnswer(int questionID, int answerID) throws NullPointerException {
		validateQuestionID(questionID);
		validateQuestionAnswerIDs(questionAnswers.get(questions.get(questionID)), answerID);
		
		questions.get(questionID).setCorrectAnswerID(answerID);
	}
	
	
	
	
	
	
	
	private void validateQuestionID(int questionID) throws NullPointerException {
		if (!questions.containsKey(questionID)) {
			throw new NullPointerException("Could not find a question with the ID of: " + questionID);
		}
	}
	
	private void validateQuizID(int quizID) throws NullPointerException {
		if (!quizes.containsKey(quizID)) {
			throw new NullPointerException("Could not find a quiz with the ID of: " + quizID);
		}
	}
	
	private void validateQuestionAnswerIDs(List<Answer> answers, int answerID) throws NullPointerException {
		boolean valid = false;
		for (Answer answer: answers) {
			if (answer.getAnswerID() == answerID) {
				valid = true;
			}
		}
		if (!valid) {
			throw new NullPointerException("Could not find a answer matching: " + answerID + ", for the question");
		}
	}
	
	

}
