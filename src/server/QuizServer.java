package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizServer {
	
	private int quizIDs;
	private int questionIDs;
	private int answerIDs;
	private int gameIDs;


	private Map<Integer, Quiz> quizes;
	private Map<Integer, Question> questions;
	private Map<Quiz, List<Game>> quizGames;

	private Map<Quiz, List<Question>> quizQuestions;
	private Map<Question, List<Answer>> questionAnswers;
	
	public QuizServer() {
		this.quizIDs = 0;
		quizes   	    = new HashMap<Integer, Quiz>();
		questions 		= new HashMap<Integer, Question>();
		quizGames 		= new HashMap<Quiz, List<Game>>();

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
	
	
	public Map<Question, List<Answer>> getQuestionsAndAnswers(int quizID) {
		validateQuizID(quizID);
		
		Map<Question, List<Answer>> result = questionAnswers;

		List<Question> questions = quizQuestions.get(quizes.get(quizID));
		result.keySet().retainAll(questions);
		return result;
		
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

	public void setQuizActive(int quizID) {
		validateQuizID(quizID);
		quizes.get(quizID).setActive(true);
	}
	
	public List<Quiz> getActiveQuizes() {
		List<Quiz> activeQuizes = new ArrayList<Quiz>();
		for (Quiz quiz : quizes.values()) {
			if (quiz.isActive()) {
				activeQuizes.add(quiz);
			}
		}
		return activeQuizes;
	}
	
	public void setQuizInactive(int quizID) 
			throws IllegalArgumentException, NullPointerException {
		validateQuizID(quizID);
		Quiz quiz = quizes.get(quizID);
		if (!getActiveQuizes().contains(quiz)) {
			throw new IllegalArgumentException("Quiz not active");
		}
		
		quiz.setActive(false);
		
		//TODO return high scores for a game
		
		
	}

	public int startGame(int quizID, String playerName) {
		validateQuizID(quizID);
		Quiz quiz = quizes.get(quizID);
		
		if (playerName.isEmpty()) {
			throw new IllegalArgumentException("Player name can not be blank");
		}
		
		if (!getActiveQuizes().contains(quiz)) {
			throw new IllegalArgumentException("Quiz not active");
		}
		
		Game game = new Game(gameIDs, playerName);
		++gameIDs;
		
		List<Game> gamesList = quizGames.get(quiz);
		
		if (gamesList == null) {
			gamesList = new ArrayList<Game>();
			quizGames.put(quiz, gamesList);
		}
		
		gamesList.add(game);
		
		return game.getGameID();
	}
	
	

}
