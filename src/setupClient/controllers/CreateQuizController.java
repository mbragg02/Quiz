package setupClient.controllers;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import server.interfaces.Answer;
import server.interfaces.Question;
import server.interfaces.Server;

public class CreateQuizController implements Controller {
	
	private Server server;
	private Scanner in;
	private int quizId;	
	
	public CreateQuizController(Server server) {
		this.server = server;
		in = new Scanner(System.in);
	}

	@Override
	public String toString() {
		return null;
	}

	@Override
	public void launch() {
		createQuizWithName();
		addQuestionsAndAnswers();
		System.out.println("Your quiz id is: " + quizId);
		setQuizStatus();
		
	}
	
	private void createQuizWithName() {
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

	
	public void addQuestionsAndAnswers() {
		String userQuestion;
		do {
			System.out.print("Please enter a question for your quiz or \"y\" when you are finished: ");
			userQuestion = in.nextLine().trim();
			if (userQuestion.trim().equalsIgnoreCase("y")) {
				if (server.getQuestionsAndAnswers(quizId).isEmpty()) {
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


	public void addAnswers(int questionId) {
		String userInput;
		do {
			System.out.print("Please enter an answer for your question or \"y\" when you are finished: ");
			userInput = in.nextLine().trim();
			if (userInput.trim().equalsIgnoreCase("y")) {
				if (server.getAnswersForQuestion(questionId).isEmpty()) {
					System.out.println("A question must have at least 1 answer");
				} else {
					break;
				}
			} else {
				try {
					server.addAnswerToQuestion(questionId, userInput);
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				} catch (NullPointerException e) {
					System.out.println(e.getMessage());
				}

			}
		} while (true);
		
		selectCorrectAnswer(questionId);
	}


	public void setQuizStatus() {
		System.out.print("Would you like to activate your new quiz? y / n : ");
		String choice = in.nextLine();
		if (choice.trim().equalsIgnoreCase("y")) {
			server.setQuizActive(quizId);
			System.out.println("Quiz active");
		} else {
			System.out.println("Quiz NOT active");
		}
	}
	
	public void selectCorrectAnswer(int questionId) {
		do {
			System.out.println("Please enter the number of the correct answer: ");
			displayQuestionsAndAnswers(questionId);

			try {
				int correctAnswer = in.nextInt();
				// minus 1 because the answers are displayed starting at 1. But stored in a 0-index array.
				server.setCorrectAnswer(questionId, correctAnswer - 1);
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
	
	protected void displayQuestionsAndAnswers(int questionID) {
		Map<Question, List<Answer>> questionsAndAnswers = server.getQuestionsAndAnswers(quizId);
		
		for (Entry<Question, List<Answer>> entry: questionsAndAnswers.entrySet()) {
			System.out.println("Q: " + entry.getKey().getQuestion());
			List<Answer> answers = entry.getValue();
			int answerCount = 1;
			for (Answer answer : answers) {
				System.out.println(answerCount + ": " + answer.getAnswer());
				++answerCount;
			}
		}

	}


}
