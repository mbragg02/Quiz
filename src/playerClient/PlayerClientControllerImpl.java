package playerClient;


import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import server.interfaces.Question;
import server.interfaces.Quiz;
import server.interfaces.Server;

public class PlayerClientControllerImpl implements PlayerClientController {

	private Scanner in;
	private Server model;
	private List<Question> questions;
	private int score;
	private int answer;
	private int quizID;
	private int gameID;
	private String playerName;
	private PlayerClientView view;

	public PlayerClientControllerImpl(Server model, PlayerClientView view) {
		this.model = model;	
		this.view = view;
		in = new Scanner(System.in);
		questions = null;
		score = 0;
		answer = 0;
		quizID = 0;
		gameID = 0;
	}
	
	@Override
	public void launch() throws RemoteException {
		view.displayWelcomeMessage();

		if (model.getActiveQuizes().isEmpty()) {
			view.displayNoActiveQuizesMessage();
			return;
		} else {
			setPlayerName();
			displayActiveQuizzes();
			setPlayerQuiz();	
			playQuiz();
		}
	}

	private void setPlayerName() {
		view.displayNameRequest();
		try {
			this.playerName = in.nextLine();
		} catch (NoSuchElementException ex) {
			view.displayException(ex.getMessage());
		}
		
	}

	@Override
	public void displayActiveQuizzes() throws RemoteException {
		List<Quiz> quizzes = model.getActiveQuizes();
		view.displayCurrentActiveQuizesMessage();
		for (Quiz quiz : quizzes) {
			view.displayQuizDetails(quiz.getQuizID(), quiz.getQuizName());
		}
	}

	@Override
	public void setPlayerQuiz() {
		do {
			try {
				view.displaySelectQuizMessage();
				this.quizID = in.nextInt();
				break;
			} catch (InputMismatchException ex) {
				view.displayException(ex.getMessage());
			}
		} while (true);
		in.nextLine();	
	}
	
	@Override
	public void playQuiz() throws RemoteException {
		do {
			try {
				this.gameID = model.startGame(this.quizID, this.playerName);
				break;
			} catch (RemoteException ex) {
				view.displayException(ex.getMessage());
			} catch (IllegalArgumentException ex) {
				view.displayException(ex.getMessage());
				setPlayerName();
			} catch (NullPointerException ex) {
				view.displayException(ex.getMessage());
				displayActiveQuizzes();
				setPlayerQuiz();
			}
		} while (true);
		
		displayQuestions();
		submitScore();
	}
	
	private void displayQuestions() {
		
		try {
			questions = model.getQuizQuestionsAndAnswers(this.quizID);
		} catch (NullPointerException | RemoteException ex) {
			view.displayException(ex.getMessage());
		}

		int questionCount = 1;
		for (Question question : questions) {
			view.displayQuestion(questionCount, question.getQuestion());
			displayAnswers(question);
			selectAnswer();
			if (answer == question.getCorrectAnswerID()) {
				++score;
			}
			++questionCount;
		}
	}
	
	private void displayAnswers(Question question) {
		int answerCount = 1;
		for (String answer : question.getAnswers()) {
			view.displayAnswer(answerCount, answer);
			++answerCount;
		}

	}

	private void selectAnswer() {
		int playerAnswer = 0;
		do {
			view.displayAnswerRequest();
			try {
				playerAnswer = in.nextInt();
				break;
			} catch (InputMismatchException ex) {
				view.displayInvlaidInputException();
				in.nextLine();
			}
		} while (true);

		// -1 because answers stored with a zero index. User interface starts an 1.
		this.answer = playerAnswer - 1;
	}
	
	@Override
	public void submitScore() {
		try {
			model.submitScore(this.quizID, this.gameID, this.score);
		} catch (NullPointerException | RemoteException e) {
			view.displayException(e.getMessage());
		}
		view.displayScoreDetails(score, questions.size());
	}

}