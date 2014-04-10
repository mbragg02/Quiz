package playerClient.views;

import java.util.Scanner;

import static setupClient.factories.MessageProperties.msg;

/**
 * All console view calls for player client.
 *
 * @author Michael Bragg
 */
public class PlayerClientView {

    private final Scanner in = new Scanner(System.in);

    public void displayWelcomeMessage() {
        System.out.println(msg("player_welcome"));
    }

    public void displayNoActiveQuizesMessage() {
        System.out.println(msg("no_active_quizzes"));
    }

    public void displayNameRequest() {
        System.out.print(msg("player_name"));
    }

    public void displayCurrentActiveQuizesMessage() {
        System.out.println(msg("active_games_header"));
    }

    public void displayQuizDetails(int quizID, String quizName) {
        System.out.println("Quiz " + quizID + ": " + quizName);
    }

    public void displaySelectQuizMessage() {
        System.out.print(msg("game_prompt"));
    }

    public void displayException(String e) {
        System.out.println(e);
    }

    public void displayInvlaidInputException() {
        System.out.println(msg("invalid_input"));
    }

    public void displayQuestion(int questionCount, String question) {
        System.out.println("Q" + questionCount + ": " + question);
    }

    public void displayAnswer(int answerCount, String answer) {
        System.out.println(" [" + answerCount + "] " + answer);
    }

    public void displayAnswerRequest() {
        System.out.print(msg("answer_select_prompt"));
    }

    public void displayScoreDetails(int score, int numberOfQuestions) {
        System.out.println("Your score was: " + score + " out of " + numberOfQuestions);
    }

    public void displayExitMessage(String playerName) {
        System.out.println(msg("player_thanks") + " " + playerName + "!");
    }

    public int getNextIntFromConsole() {
        return in.nextInt();
    }

    public String getNextLineFromConsole() {
        return in.nextLine();
    }

}
