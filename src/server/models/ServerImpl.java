package server.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.interfaces.Answer;
import server.interfaces.Game;
import server.interfaces.Question;
import server.interfaces.Quiz;
import server.interfaces.Server;

public class ServerImpl implements Server {

	private int quizIDs;
	private int questionIDs;
	private int answerIDs;
	private int gameIDs;

	private Map<Integer, Quiz> quizes;
	private Map<Integer, Question> questions;
	private Map<Quiz, List<Game>> quizGames;
	private Map<Quiz, List<Question>> quizQuestions;
	private Map<Question, List<Answer>> questionAnswers;

	public ServerImpl() {
		this.quizIDs = 0;
		this.questionIDs = 0;
		this.answerIDs = 0;
		this.gameIDs = 0;
		
		quizes   	    = new HashMap<Integer, Quiz>();
		questions 		= new HashMap<Integer, Question>();
		quizGames 		= new HashMap<Quiz, List<Game>>();
		quizQuestions   = new HashMap<Quiz, List<Question>>();
		questionAnswers = new HashMap<Question, List<Answer>>();	
	}
	
	public void launch() {
		System.out.println("Quiz Server running");	
	}

	@Override
	public int createQuiz(String quizName) 
			throws IllegalArgumentException, NullPointerException {

		if (quizName == null) {
			throw new NullPointerException("Quiz name was null");
		}

		if (quizName.isEmpty()) {
			throw new IllegalArgumentException("Quiz name can not be blank");
		}

		Quiz quiz = new QuizImpl(quizIDs, quizName);

		quizes.put(quiz.getQuizID(), quiz);

		if(quizQuestions.get(quiz) == null) {
			quizQuestions.put(quiz, new ArrayList<Question>());
		}

		++quizIDs;
		return quiz.getQuizID();
	}

	@Override
	public int addQuestionToQuiz(int quizID, String quizQuestion) 
			throws IllegalArgumentException, NullPointerException {

		if (quizQuestion.isEmpty()) {
			throw new IllegalArgumentException("Question can not be blank");
		}

		validateQuizID(quizID);

		Question question = new QuestionImpl(questionIDs, quizQuestion);

		questions.put(questionIDs, question);

		quizQuestions.get(quizes.get(quizID)).add(question);

		questionAnswers.put(question, new ArrayList<Answer>());

		++questionIDs;
		return question.getQuestionID();
	}

	@Override
	public int addAnswerToQuestion(int questionID, String quizAnswer) 
			throws IllegalArgumentException, NullPointerException {

		if (quizAnswer.isEmpty()) {
			throw new IllegalArgumentException("Answer can not be blank");
		}
		validateQuestionID(questionID);

		Answer answer = new AnswerImpl(answerIDs, quizAnswer);

		questionAnswers.get(questions.get(questionID)).add(answer);
		++answerIDs;
		return answer.getAnswerID();
	}

	@Override
	public void setCorrectAnswer(int questionID, int answerID) throws NullPointerException {
		validateQuestionID(questionID);
		validateQuestionAnswerIDs(questionAnswers.get(questions.get(questionID)), answerID);

		questions.get(questionID).setCorrectAnswerID(answerID);
	}


	@Override
	public Map<Question, List<Answer>> getQuestionsAndAnswers(int quizID) {
		validateQuizID(quizID);

		Map<Question, List<Answer>> result = questionAnswers;

		List<Question> questions = quizQuestions.get(quizes.get(quizID));
		result.keySet().retainAll(questions);
		return result;
	}
	
	@Override
	public List<Answer> getAnswersForQuestion(int questionID) {
		Question question = questions.get(questionID);
		List<Answer> answers = questionAnswers.get(question);
		return answers;
	}


	@Override
	public void setQuizActive(int quizID) {
		validateQuizID(quizID);
		quizes.get(quizID).setActive(true);
	}

	@Override
	public List<Quiz> getActiveQuizes() {
		List<Quiz> activeQuizes = new ArrayList<Quiz>();
		for (Quiz quiz : quizes.values()) {
			if (quiz.isActive()) {
				activeQuizes.add(quiz);
			}
		}
		return activeQuizes;
	}

	@Override
	public List<Game> setQuizInactive(int quizID) 
			throws IllegalArgumentException, NullPointerException {
		validateQuizID(quizID);
		Quiz quiz = quizes.get(quizID);
		if (!getActiveQuizes().contains(quiz)) {
			throw new IllegalArgumentException("Quiz not active");
		}

		quiz.setActive(false);
		
		List<Game> playedGames = quizGames.get(quiz);
		List<Game> result;
		
		if (playedGames == null) {
			result = new ArrayList<Game>();
		} else {
			result = getHighscoreGames(playedGames);
		}

		return result;
	}
	
	private List<Game> getHighscoreGames(List<Game> gamesList) {
		int highscore = 0;
		List<Game> result = new ArrayList<Game>();

		// Calculate the highest score
		for (Game game : gamesList) {
			if (game.getScore() > highscore) {
				highscore = game.getScore();
			}
		}
		// Find the games with that high score
		for (Game game : gamesList) {
			if (game.getScore() == highscore) {
				result.add(game);
			}
		}
		return result;
	}

	@Override
	public int startGame(int quizID, String playerName) 
			throws IllegalArgumentException, NullPointerException {
		validateQuizID(quizID);
		Quiz quiz = quizes.get(quizID);

		if (playerName.isEmpty()) {
			throw new IllegalArgumentException("Player name can not be blank");
		}

		if (!getActiveQuizes().contains(quiz)) {
			throw new IllegalArgumentException("Quiz not active");
		}

		Game game = new GameImpl(gameIDs, playerName);
		++gameIDs;

		List<Game> gamesList = quizGames.get(quiz);

		if (gamesList == null) {
			gamesList = new ArrayList<Game>();
			quizGames.put(quiz, gamesList);
		}

		gamesList.add(game);

		return game.getGameID();
	}

	@Override
	public void submitScore(int quizID, int gameID, int score) throws NullPointerException {
		validateQuizID(quizID);

		Quiz quiz = quizes.get(quizID);
		List<Game> games = quizGames.get(quiz);
		
		if (games == null) {
			throw new NullPointerException("Could not find any games for that quiz.");
		}
		Game result = null;
		for (Game game: games) {
			if(game.getGameID() == gameID) {
				result = game;
			}
		}
		if (result == null) {
			throw new NullPointerException("Could not a game with that game ID.");
		}
		
		result.setScore(score);
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
		boolean invalid = true;
		for (Answer answer: answers) {
			if (answer.getAnswerID() == answerID) {
				invalid = false;
			}
		}
		if (invalid) {
			throw new NullPointerException("Could not find a answer matching: " + answerID + ", for the question");
		}
	}

}
