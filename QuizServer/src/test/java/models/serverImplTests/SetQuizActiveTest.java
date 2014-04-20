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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Test for the setQuizActive method in serverImpl
 * Also tests validateInactiveQuizzes
 *
 * @author Michael Bragg
 */
public class SetQuizActiveTest {
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

    // setQuizActive
    @Test
    public void testSetQuizActive() throws RemoteException {
        when(data.getQuiz(10)).thenReturn(quiz);

        server.setQuizActive(10);

        verify(quiz).setActive(true);
    }

    @Test(expected = NullPointerException.class)
    public void testSetQuizActiveException() throws RemoteException {
        when(data.getQuiz(10)).thenReturn(quiz);
        when(quiz.isActive()).thenReturn(true);

        server.setQuizActive(10);
    }

}
