package server.models;

import server.interfaces.Question;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Bragg
 * A question class which contains multiple answers.
 */
public class QuestionImpl implements Question, Serializable {

    private static final long serialVersionUID = 2103675520207133041L;
    private int questionID;
    private String question;
    private int correctAnswerID;
    private final List<String> answers;

    public QuestionImpl(int questionID, String quizQuestion) {
        setQuestionID(questionID);
        setQuestion(quizQuestion);
        answers = new ArrayList<>();
    }

    @Override
    public int getQuestionID() {
        return questionID;
    }

    @Override
    public String getQuestion() {
        return question;
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

    private void setQuestionID(int questionID) {
        this.questionID = questionID;
    }
    private void setQuestion(String question) {
        this.question = question;
    }
}
