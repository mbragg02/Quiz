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
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Test for the getInactiveQuizzes method in serverImpl
 *
 * @author Michael Bragg
 */
public class GetInactiveQuizzesTest {
    private Server server;

    @Mock
    private ServerData data;
    @Mock
    private QuizFactory factory;
    @Mock
    private Quiz quiz;
    @Mock
    private List<Quiz> quizzes;
    @Mock
    private ConcurrentHashMap<Integer, Quiz> quizHashMap;
    @Mock
    private Iterator<Quiz> quizIterator;

    @Before
    public void setUp() throws RemoteException {
        initMocks(this);
        this.server = new ServerImpl(factory, data);
    }

    // getInactiveQuizzes
    @Test
    public void testGetInactiveQuizzes() throws RemoteException {
        when(quiz.isActive()).thenReturn(false);
        quizzes.add(0, quiz);

        when(data.getQuizzes()).thenReturn(quizHashMap);
        when(quizHashMap.values()).thenReturn(quizzes);

        when(quizzes.iterator()).thenReturn(quizIterator);
        when(quizIterator.hasNext()).thenReturn(true).thenReturn(false);
        when(quizIterator.next()).thenReturn(quiz);

        List<Quiz> result = server.getInactiveQuizzes();
        assertEquals(1, result.size());
    }
}
