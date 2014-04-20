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

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Test for the addQuizToQuestion method in serverImpl
 *
 * @author Michael Bragg
 */
public class AddQuizToQuestionTest {

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

    // addQuestionToQuiz
    @Test
    public void testAddQuestionsToQuiz() throws Exception {
        when(factory.getQuestion(anyInt(), eq("test question"))).thenReturn(question);
        when(data.getQuiz(anyInt())).thenReturn(quiz);

        int questionID = server.addQuestionToQuiz(10, "test question");

        verify(factory).getQuestion(anyInt(), eq("test question"));
        verify(data).getQuiz(10);
        verify(quiz).addQuestion(question);

        assertEquals(0, questionID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddBlankQuestionsToQuiz() throws Exception {
        server.addQuestionToQuiz(10, "");
    }

    @Test(expected = NullPointerException.class)
    public void testAddBlankQuestionsToQuizNull() throws Exception {
        server.addQuestionToQuiz(99, "Some question");
    }

}
