package setupClient.views;

public class CreateQuizView extends SetupClientView {
	
	public void printQuizID(int quizID) {
		System.out.println("Your quiz id is: " + quizID);
	}
	
	public void printNameInputRequest() {
		System.out.print("Please enter a name for your quiz: ");
	}
	
	public void printQuestionInputRequest() {
		System.out.print("Please enter a question for your quiz or \"y\" when you are finished: ");
	}
	
	public void printQuestionNumberException() {
		System.out.println("A quiz must have at least 1 question");
	}
	
	public void printAnswerInputRequest() {
		System.out.print("Please enter an answer for your question or \"y\" when you are finished: ");
	}
	
	public void printAnswerNumberException() {
		System.out.println("A question must have at least 1 answer");
	}
	
	public void printCorrectAnswerRequest() {
		System.out.println("Please enter the number of the correct answer: ");
	}
	
	public void printActivationRequest() {
		System.out.print("Would you like to activate your new quiz? y / n : ");
	}
	
	public void printActiveMessage() {
		System.out.println("Quiz active");
	}
	
	public void printInActiveMessage() {
		System.out.println("Quiz Not active");
	}
	
	public void printQuestion(String question) {
		System.out.println("Q: " + question);
	}
	public void printAnswer(int answerNumber, String answer) {
		System.out.println(answerNumber + ": " + answer);
	}

}
