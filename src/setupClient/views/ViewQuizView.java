package setupClient.views;

public class ViewQuizView {
	
	public void displayNoActiveQuizesMessage() {
		System.out.println("Currently no active quizzes");
	}
	
	public void displayQuizDetails(int quizID, String quizName) {
		System.out.println(quizID + ": " + quizName);
	}

}
