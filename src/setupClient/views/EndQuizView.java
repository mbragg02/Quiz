package setupClient.views;

/**
 * View for messages relating to ending an active Quiz.
 *
 * @author Michael Bragg
 */
public class EndQuizView extends SetupClientView {

    public void printQuizIDDeactivationRequest() {
        System.out.print("Please enter a Quiz ID to deactivate: ");
    }

    public void printNoPlayersMessage() {
        System.out.println("There were no players that took the quiz");
    }

    public void printWinnersAreMessage() {
        System.out.println("And the winners are...");
    }

    public void printWinnerDetails(int count, String name, Double score, String date) {
        System.out.println("[" + count + "] " + name + " with a score of: " + score + "%" + " on " + date);
    }
}