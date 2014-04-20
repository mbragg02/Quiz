package models;

import interfaces.Question;
import interfaces.Quiz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for a quiz. A quiz has a unique ID and a list of questions.
 *
 * @author Michael Bragg
 */
public class QuizImpl implements Quiz, Serializable {

    private static final long serialVersionUID = 2700824645029004884L;
    private int QuizID;
    private String quizName;
    private boolean active;
    private final List<Question> questions;

    public QuizImpl(int quizID, String quizName) {
        setQuizID(quizID);
        setQuizName(quizName);
        questions = new ArrayList<>();
    }

    @Override
    public String getQuizName() {
        return quizName;
    }

    @Override
    public int getQuizID() {
        return QuizID;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public Question getQuestion(int questionId) {

        for (Question question : questions) {
            if (question.getQuestionID() == questionId) {
                return question;
            }

        }
        return null;
    }

    @Override
    public List<Question> getQuestions() {
        return this.questions;
    }

    @Override
    public void addQuestion(Question quizQuestion) {
        questions.add(quizQuestion);
    }

    private void setQuizID(int quizID) {
        QuizID = quizID;
    }

    private void setQuizName(String quizName) {
        this.quizName = quizName;
    }

}
