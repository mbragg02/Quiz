package setupClient.controllers;

import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import server.interfaces.Question;
import server.interfaces.Server;
import setupClient.views.CreateQuizView;

public class CreateQuizController extends Controller {
	
	private Scanner in;
	private int quizID;	
	private CreateQuizView view;
	
	public CreateQuizController(Server model, CreateQuizView view) {
		super(model);
		this.view = view;
		in = new Scanner(System.in);
	}
	
	@Override
	public void launch() throws RemoteException {
		createQuizWithName();
		addQuestionsAndAnswers();
		view.printQuizID(quizID);
		setQuizStatus();
		
	}
	
	private void createQuizWithName() throws RemoteException, NullPointerException {
		do {
			view.printNameInputRequest();
			String quizName = in.nextLine().trim();
			try {
				this.quizID = model.createQuiz(quizName);	
				break;
			} catch(IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		} while (true);
	}

	
	public void addQuestionsAndAnswers() throws RemoteException {
		String userQuestion;
		do {
			view.printQuestionInputRequest();
			userQuestion = in.nextLine().trim();
			if (userQuestion.trim().equalsIgnoreCase("y")) {
				if (model.getQuizQuestionsAndAnswers(quizID).isEmpty()) {
					view.printAnswerNumberException();
				} else {
					break;
				}
			} else {
				int questionId;
				try {
					questionId = model.addQuestionToQuiz(quizID, userQuestion);
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
			view.printAnswerInputRequest();
			userInput = in.nextLine().trim();
			if (userInput.trim().equalsIgnoreCase("y")) {
				if (model.getAnswersForQuestion(quizID, questionId).isEmpty()) {
					view.printAnswerNumberException();
				} else {
					break;
				}
			} else {
				try {
					model.addAnswerToQuestion(quizID, questionId, userInput);
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
			view.printCorrectAnswerRequest();
			displayQuestionsAndAnswers(questionId);

			try {
				int correctAnswer = in.nextInt();
				model.setCorrectAnswer(quizID, questionId, correctAnswer - 1);
				break;
			} 
			catch (InputMismatchException e) {
				view.printInvalidInputException();
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			} finally {
				in.nextLine();
			}
		} while (true);

	}
	
	protected void displayQuestionsAndAnswers(int questionID) throws RemoteException, NullPointerException {
		List<Question> questions = model.getQuizQuestionsAndAnswers(quizID);
		for (Question question : questions) {
			if(question.getQuestionID() == questionID) {
				view.printQuestion(question.getQuestion());
				List<String> answers = question.getAnswers();
				int answerCount = 1;
				for (String answer : answers) {
					view.printAnswer(answerCount, answer);
					++answerCount;
				}
			}

		}

	}
	
	public void setQuizStatus() throws RemoteException {
		view.printActivationRequest();
		String choice = in.nextLine();
		if (choice.trim().equalsIgnoreCase("y")) {
			model.setQuizActive(quizID);
			view.printActiveMessage();
		} else {
			view.printInActiveMessage();
		}
	}


}
