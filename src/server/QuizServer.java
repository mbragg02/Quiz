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

	public int createQuiz(String quizName) throws IllegalArgumentException, NullPointerException {
		
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

	public int addQuestionToQuiz(int quizID, String quizQuestion) throws IllegalArgumentException {
		
		if (quizQuestion.isEmpty()) {
			throw new IllegalArgumentException("Question can not be blank");
		}
		
		Question question = new Question(questionIDs, quizQuestion);
		
		questions.put(questionIDs, question);
		
		quizQuestions.get(quizes.get(quizID)).add(question);
		
		questionAnswers.put(question, new ArrayList<Answer>());
		
		++questionIDs;
		return question.getQuestionID();
	}

	public int addAnswerToQuestion(int questionID, String quizAnswer) {
		if (quizAnswer.isEmpty()) {
			throw new IllegalArgumentException("Answer can not be blank");
		}
		
		Answer answer = new Answer(answerIDs, quizAnswer);
		
		questionAnswers.get(questions.get(questionID)).add(answer);
		++answerIDs;
		return answer.getAnswerID();
	}
	
	

}
