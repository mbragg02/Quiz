package setupClient.views;

/**
 * View for displaying a quizzes details
 *
 * @author Michael Bragg
 */
public class DisplayQuizView extends SetupClientView {
	
	public void printQuizDetails(int quizID, String quizName) {
		System.out.println(quizID + ": " + quizName);
	}

}
