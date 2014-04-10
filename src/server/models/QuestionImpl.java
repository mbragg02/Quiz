package server.models;

import server.interfaces.Question;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A question class which contains multiple answers.
 *
 * @author Michael Bragg
 */
public class QuestionImpl implements Question, Serializable {

    private static final long serialVersionUID = 2103675520207133041L;
    private final List<String> answers;
    private int questionID;
    private String question;
    private int correctAnswerID;

    public QuestionImpl(int questionID, String quizQuestion) {
        setQuestionID(questionID);
        setQuestion(quizQuestion);
        answers = new ArrayList<>();
    }

    @Override
    public int getQuestionID() {
        return questionID;
    }

    private void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    private void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public int getCorrectAnswerID() {
        return correctAnswerID;
    }

    @Override
    public void setCorrectAnswerID(int correctAnswerID) {
        this.correctAnswerID = correctAnswerID;
    }

    @Override
    public void addAnswer(String answer) {
        answers.add(answer);
    }

    @Override
    public List<String> getAnswers() {
        return this.answers;
    }
}
