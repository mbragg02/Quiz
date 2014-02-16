package server.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import server.interfaces.Game;
import server.interfaces.Question;
import server.interfaces.Quiz;
import server.interfaces.Server;

public class ServerImpl implements Server {

	private int quizIDs;
	private int questionIDs;
	private int gameIDs;

	private Map<Integer, Quiz> quizes;
	private Map<Integer, List<Game>> games;

//	private Map<Integer, Question> questions;
//	private Map<Quiz, List<Game>> quizGames;
//	private Map<Quiz, List<Question>> quizQuestions;

	public ServerImpl() {
		this.quizIDs = 0;
		this.questionIDs = 0;
		this.gameIDs = 0;
		
		quizes   	    = new HashMap<Integer, Quiz>();
		games   	    = new HashMap<Integer, List<Game>>();
//		questions 		= new HashMap<Integer, Question>();
//		quizGames 		= new HashMap<Quiz, List<Game>>();
//		quizQuestions   = new HashMap<Quiz, List<Question>>();
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
		
		quizes.get(quizID).addQuestion(question);	


		++questionIDs;
		return question.getQuestionID();
	}

	@Override
	public void addAnswerToQuestion(int quizID, int questionID, String quizAnswer) 
			throws IllegalArgumentException, NullPointerException {

		if (quizAnswer.isEmpty()) {
			throw new IllegalArgumentException("Answer can not be blank");
		}
		quizes.get(quizID).getQuestion(questionID).addAnswer(quizAnswer);

	}

	@Override
	public void setCorrectAnswer(int quizID, int questionID, int correctAnswer) throws NullPointerException {

		int numberOfAnswers = quizes.get(quizID).getQuestion(questionID).getAnswers().size();
		
		// numberOfAnswers - 1 to get the highest answer index
		if (correctAnswer > numberOfAnswers - 1 || correctAnswer < 0 ) {
			throw new IllegalArgumentException("Not a valid answer");
		}
		quizes.get(quizID).getQuestion(questionID).setCorrectAnswerID(correctAnswer);	
	}

	@Override
	public List<Question> getQuizQuestionsAndAnswers(int quizID) throws NullPointerException{
		validateQuizID(quizID);
		return quizes.get(quizID).getQuestions();		
	}

	@Override
	public List<String> getAnswersForQuestion(int quizID, int questionID) throws NullPointerException {
		return quizes.get(quizID).getQuestion(questionID).getAnswers();
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
		
		List<Game> playedGames = games.get(quiz);
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

		List<Game> gamesList = games.get(quiz);

		if (gamesList == null) {
			gamesList = new ArrayList<Game>();
			games.put(quizID, gamesList);
		}

		gamesList.add(game);

		return game.getGameID();
	}

	@Override
	public void submitScore(int quizID, int gameID, int score) throws NullPointerException {
		validateQuizID(quizID);

		List<Game> gamesList = games.get(quizID);
		
		if (gamesList == null) {
			throw new NullPointerException("Could not find any games for that quiz.");
		}
		Game result = null;
		
		for (Game game: gamesList) {
			if(game.getGameID() == gameID) {
				result = game;
			}
		}
		if (result == null) {
			throw new NullPointerException("Could not a game with that game ID.");
		}
		
		result.setScore(score);
	}


	private void validateQuizID(int quizID) throws NullPointerException {
		if (!quizes.containsKey(quizID)) {
			throw new NullPointerException("Could not find a quiz with the ID of: " + quizID);
		}
	}


}
