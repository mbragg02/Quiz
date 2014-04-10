package server.interfaces;

import java.util.List;

/**
 * A full quiz.
 *
 * @author Michael Bragg
 */
public interface Quiz {

    /**
     * Get the name of the quiz
     * @return String. The quiz name
     */
	String getQuizName();

    /**
     * Get the quizzes unique ID
     * @return int. ID
     */
	int getQuizID();

    /**
     * If this quiz is currently active. I.e playable
     * @return boolean.
     */
	boolean isActive();

    /**
     * Set the quiz to be active. i.e playable
     * @param active boolean.
     */
	void setActive(boolean active);

    /**
     * Get a question for this quiz.
     * @param questionId int. A question ID
     * @return a Question object.
     */
	Question getQuestion(int questionId);

    /**
     * Get a list of all the questions for this quiz.
     * @return List<Question> questions
     */
	List<Question> getQuestions();

    /**
     * Add a question to this quiz.
     * @param quizQuestion Question.
     */
	void addQuestion(Question quizQuestion);

}