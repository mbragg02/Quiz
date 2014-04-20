package models.serverImplTests;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import factories.QuizFactory;
import interfaces.Question;
import interfaces.Quiz;
import interfaces.Server;
import models.ServerData;
import models.ServerImpl;

import java.rmi.RemoteException;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Test for the addAnswerToQuestion method in serverImpl
 * Also test validateQuizAndQuestionID private method.
 *
 * @author Michael Bragg
 */
public class AddAnswerToQuestionTest {

    private Server server;

    @Mock
    private ServerData data;
    @Mock
    private QuizFactory factory;
    @Mock
    private Quiz quiz;
    @Mock
    private Question question;

    @Before
    public void setUp() throws RemoteException {
        initMocks(this);
        this.server = new ServerImpl(factory, data);
    }

    // addAnswerToQuestion
    @Test
    public void testAddAnswerToQuestion() throws Exception {
        when(data.getQuiz(10)).thenReturn(quiz);
        when(quiz.getQuestion(20)).thenReturn(question);

        server.addAnswerToQuestion(10, 20, "some answer");

        verify(data, times(2)).getQuiz(anyInt());
        verify(quiz, times(2)).getQuestion(anyInt());
        verify(question).addAnswer("some answer");
    }

    @Test(expected = NullPointerException.class)
    public void testAddAnswerToQuestionUnknownQuestionID() throws Exception {
        when(data.getQuiz(10)).thenReturn(quiz);
        when(quiz.getQuestion(20)).thenReturn(null);
        server.addAnswerToQuestion(10, 20, "some answer");
    }

    @Test(expected = NullPointerException.class)
    public void testAddAnswerToQuestionUnknownQuizID() throws Exception {
        when(data.getQuiz(10)).thenReturn(null);
        server.addAnswerToQuestion(10, 20, "some answer");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddBlankAnswer() throws Exception {
        server.addAnswerToQuestion(10, 20, "");
    }
}
