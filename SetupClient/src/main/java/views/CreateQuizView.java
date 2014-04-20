package views;

import static utilities.MessageProperties.msg;

/**
 * View for messages relating to creating a new Quiz.
 *
 * @author Michael Bragg
 */
public class CreateQuizView extends SetupClientView {

    public void printQuizID(int quizID) {
        System.out.println(msg("your_quizid") + " " + quizID);
    }

    public void printNameInputRequest() {
        System.out.print(msg("quiz_name_prompt") + " ");
    }

    public void printQuestionInputRequest() {
        System.out.print(msg("question_prompt") + " ");
    }

    public void printQuestionNumberException() {
        System.out.println(msg("question_warning"));
    }

    public void printAnswerInputRequest() {
        System.out.print(msg("answer_prompt") + " ");
    }

    public void printAnswerNumberException() {
        System.out.println(msg("answer_warning"));
    }

    public void printCorrectAnswerRequest() {
        System.out.println(msg("correct_answer_prompt") + " ");
    }

    public void printActivationRequest() {
        System.out.print(msg("activation_prompt") + " ");
    }

    public void printActiveMessage() {
        System.out.println(msg("quiz_active"));
    }

    public void printInActiveMessage() {
        System.out.println(msg("quiz_inactive"));
    }

    public void printQuestion(String question) {
        System.out.println("Q: " + question);
    }

    public void printAnswer(int answerNumber, String answer) {
        System.out.println(answerNumber + ": " + answer);
    }
}