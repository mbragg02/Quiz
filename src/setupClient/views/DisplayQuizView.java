package setupClient.views;

/**
 * @author Michael Bragg
 * View for displaying a quizzes details
 */
public class DisplayQuizView extends SetupClientView {
	
	public void printQuizDetails(int quizID, String quizName) {
		System.out.println(quizID + ": " + quizName);
	}

}
