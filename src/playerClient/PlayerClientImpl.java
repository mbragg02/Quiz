package playerClient;


import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import server.interfaces.Question;
import server.interfaces.Quiz;
import server.interfaces.Server;

public class PlayerClientImpl implements PlayerClient {

	private Scanner in;
	private Server server;
	private List<Question> questions;
	private int score;
	private int answer;
	private int quizID;
	private int gameID;
	private String playerName;

	public PlayerClientImpl(Server server) {
		this.server = server;			
		in = new Scanner(System.in);
		questions = null;
		score = 0;
		answer = 0;
		quizID = 0;
		gameID = 0;
	}
	
	@Override
	public void launch() throws RemoteException {
		System.out.println("Welcome to Quiz Player Client");

		if (server.getActiveQuizes().isEmpty()) {
			System.out.println("Currently no active quizzes. Please try again later.");
			return;
		} else {
			setPlayerName();
			displayActiveQuizzes();
			setPlayerQuiz();	
			playQuiz();
		}
	}

	private void setPlayerName() {
		System.out.print("Please enter your name: ");
		try {
			this.playerName = in.nextLine();
		} catch (NoSuchElementException e) {
			System.out.println(e.getMessage());
		}
		
	}

	@Override
	public void displayActiveQuizzes() throws RemoteException {
		List<Quiz> quizzes = server.getActiveQuizes();
		System.out.println("Current active games: ");
		for (Quiz quiz : quizzes) {
			System.out.println("Quiz " + quiz.getQuizID() + ": " + quiz.getQuizName());
		}
	}

	@Override
	public void setPlayerQuiz() {
		do {
			try {
				System.out.print("Please select a quiz to play: ");
				this.quizID = in.nextInt();
				break;
			} catch (InputMismatchException ex) {
				System.out.println(ex.getMessage());
			}
		} while (true);
		in.nextLine();	
	}
	
	@Override
	public void playQuiz() throws RemoteException {
		do {
			try {
				this.gameID = server.startGame(this.quizID, this.playerName);
				break;
			} catch (RemoteException ex) {
				System.out.println(ex.getMessage());
			} catch (IllegalArgumentException ex) {
				System.out.println(ex.getMessage());
				setPlayerName();
			} catch (NullPointerException ex) {
				System.out.println(ex.getMessage());
				displayActiveQuizzes();
				setPlayerQuiz();
			}
		} while (true);
		
		displayQuestions();
		submitScore();
	}
	
	private void displayQuestions() {
		
		try {
			questions = server.getQuizQuestionsAndAnswers(this.quizID);
		} catch (NullPointerException | RemoteException e) {
			System.out.println(e.getMessage());
		}

		int questionCount = 1;
		for (Question question : questions) {
			System.out.println("Q" + questionCount + ": " + question.getQuestion());
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
			System.out.println(" [" + answerCount + "] " + answer);
			++answerCount;
		}

	}

	private void selectAnswer() {
		int playerAnswer = 0;
		do {
			System.out.print("Please select an answer: ");
			try {
				playerAnswer = in.nextInt();
				break;
			} catch (InputMismatchException ex) {
				System.out.println("Invalid input");
				in.nextLine();
			}
		} while (true);

		// -1 because answers stored with a zero index. User interface starts an 1.
		this.answer = playerAnswer - 1;
	}
	
	@Override
	public void submitScore() {
		try {
			server.submitScore(this.quizID, this.gameID, this.score);
		} catch (NullPointerException | RemoteException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Your score was: " + score + " out of " + questions.size());
	}

}