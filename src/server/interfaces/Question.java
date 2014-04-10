package server.interfaces;

import java.util.List;

/**
 * A question for a quiz - with multiple answers.
 *
 * @author Michael Bragg
 */
public interface Question {

    /**
     * Get the questions unique ID
     *
     * @return int. Question ID
     */
    int getQuestionID();

    /**
     * Get the question
     *
     * @return String. The Question
     */
    String getQuestion();

    /**
     * Get the ID of the correct answer.
     *
     * @return int.
     */
    int getCorrectAnswerID();

    /**
     * Set an answer as being correct.
     *
     * @param correctAnswerID int. Correct answer ID
     */
    void setCorrectAnswerID(int correctAnswerID);

    /**
     * Add an answer to the question.
     *
     * @param answer String. A answer
     */
    void addAnswer(String answer);

    /**
     * Get a list of all the answers for this question.
     *
     * @return List<String> answers
     */
    List<String> getAnswers();

}