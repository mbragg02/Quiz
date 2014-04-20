package models;

import interfaces.Question;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class QuestionImplTest {
    private Question question;

    @Before
    public void setUp() {
        this.question = new QuestionImpl(0, "TestQuestion");
    }

    @Test
    public void testGetID() {
        assertEquals(0, question.getQuestionID());
    }

    @Test
    public void testGetQuestion() {
        assertEquals("TestQuestion", question.getQuestion());
    }

    @Test
    public void testAddAnswer() {
        question.addAnswer("TestAnswer");
        assertFalse(question.getAnswers().isEmpty());
    }

    @Test
    public void testGetAnswers() {
        assertTrue(question.getAnswers().isEmpty());
    }

    @Test
    public void testSetAndGetCorrectAnswer() {
        question.addAnswer("TestAnswer");
        question.setCorrectAnswerID(1);
        assertEquals("TestAnswer", question.getAnswers().iterator().next());
    }
}