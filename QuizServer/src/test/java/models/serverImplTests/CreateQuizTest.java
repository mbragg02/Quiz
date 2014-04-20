package models.serverImplTests;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import factories.QuizFactory;
import interfaces.Quiz;
import interfaces.Server;
import models.ServerData;
import models.ServerImpl;

import java.rmi.RemoteException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Test for the createQuiz method in serverImpl
 *
 * @author Michael Bragg
 */
public class CreateQuizTest {

    private Server server;

    @Mock
    private QuizFactory factory;
    @Mock
    private ServerData data;
    @Mock
    private Quiz quiz;

    @Before
    public void setUp() throws RemoteException {
        initMocks(this);
        this.server = new ServerImpl(factory, data);
    }

    // createQuiz
    @Test
    public void testCreateQuiz() throws Exception {
        when(factory.getQuiz(anyInt(), anyString())).thenReturn(quiz);
        when(quiz.getQuizID()).thenReturn(10);

        int quizID = server.createQuiz("test quiz");

        verify(data).addQuiz(10, quiz);
        assertEquals(10, quizID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateQuizBlankName() throws Exception {
        server.createQuiz("");
    }

    @Test(expected = NullPointerException.class)
    public void testCreateQuizNullName() throws Exception {
        server.createQuiz(null);
    }

}