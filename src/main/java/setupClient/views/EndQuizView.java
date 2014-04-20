package setupClient.views;

import static server.utilities.MessageProperties.msg;

/**
 * View for messages relating to ending an active Quiz.
 *
 * @author Michael Bragg
 */
public class EndQuizView extends SetupClientView {

    public void printQuizIDDeactivationRequest() {
        System.out.print(msg("quizid_deactivation_prompt")  + " ");
    }

    public void printNoPlayersMessage() {
        System.out.println(msg("no_players"));
    }

    public void printWinnersAreMessage() {
        System.out.println(msg("winners_header"));
    }

    public void printWinnerDetails(int count, String name, Double score, String date) {
        System.out.println("[" + count + "] " + name + " with a score of: " + score + "%" + " on " + date);
    }
}