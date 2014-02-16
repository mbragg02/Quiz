package setupClient.controllers;

import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import server.interfaces.Question;
import server.interfaces.Server;

public class CreateQuizController extends Controller {
	
	private Scanner in;
	private int quizId;	
	
	public CreateQuizController(Server server) {
		super(server);
		in = new Scanner(System.in);
	}
	
	@Override
	public void launch() throws RemoteException {
		createQuizWithName();
		addQuestionsAndAnswers();
		System.out.println("Your quiz id is: " + quizId);
		setQuizStatus();
		
	}
	
	private void createQuizWithName() throws RemoteException, NullPointerException {
		do {
			System.out.print("Please enter a name for your quiz: ");
			String quizName = in.nextLine().trim();
			try {
				this.quizId = server.createQuiz(quizName);	
				break;
			} catch(IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		} while (true);
	}

	
	public void addQuestionsAndAnswers() throws RemoteException {
		String userQuestion;
		do {
			System.out.print("Please enter a question for your quiz or \"y\" when you are finished: ");
			userQuestion = in.nextLine().trim();
			if (userQuestion.trim().equalsIgnoreCase("y")) {
				if (server.getQuizQuestionsAndAnswers(quizId).isEmpty()) {
					System.out.println("A quiz must have at least 1 question");
				} else {
					break;
				}
			} else {
				int questionId;
				try {
					questionId = server.addQuestionToQuiz(quizId, userQuestion);
					addAnswers(questionId);
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				} catch (NullPointerException e) {
					System.out.println(e.getMessage());
				}
			}
		} while (true);
	}


	public void addAnswers(int questionId) throws RemoteException, NullPointerException {
		String userInput;
		do {
			System.out.print("Please enter an answer for your question or \"y\" when you are finished: ");
			userInput = in.nextLine().trim();
			if (userInput.trim().equalsIgnoreCase("y")) {
				if (server.getAnswersForQuestion(quizId, questionId).isEmpty()) {
					System.out.println("A question must have at least 1 answer");
				} else {
					break;
				}
			} else {
				try {
					server.addAnswerToQuestion(quizId, questionId, userInput);
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				} catch (NullPointerException e) {
					System.out.println(e.getMessage());
				}

			}
		} while (true);
		
		selectCorrectAnswer(questionId);
	}

	
	public void selectCorrectAnswer(int questionId) throws RemoteException, NullPointerException {
		do {
			System.out.println("Please enter the number of the correct answer: ");
			displayQuestionsAndAnswers(questionId);

			try {
				int correctAnswer = in.nextInt();
				server.setCorrectAnswer(quizId, questionId, correctAnswer - 1);
				break;
			} 
			catch (InputMismatchException e) {
				System.out.println("Not a valid number");
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			} finally {
				in.nextLine();
			}
		} while (true);

	}
	
	protected void displayQuestionsAndAnswers(int questionID) throws RemoteException, NullPointerException {
		List<Question> questions = server.getQuizQuestionsAndAnswers(quizId);
		for (Question question : questions) {
			if(question.getQuestionID() == questionID) {
				System.out.println("Q: " + question.getQuestion());
				List<String> answers = question.getAnswers();
				int answerCount = 1;
				for (String answer : answers) {
					System.out.println(answerCount + ": " + answer);
					++answerCount;
				}
			}

		}

	}
	
	public void setQuizStatus() throws RemoteException {
		System.out.print("Would you like to activate your new quiz? y / n : ");
		String choice = in.nextLine();
		if (choice.trim().equalsIgnoreCase("y")) {
			server.setQuizActive(quizId);
			System.out.println("Quiz active");
		} else {
			System.out.println("Quiz NOT active");
		}
	}


}
