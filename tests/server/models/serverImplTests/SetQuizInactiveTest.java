package server.models.serverImplTests;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import server.factories.QuizFactory;
import server.interfaces.Game;
import server.interfaces.Quiz;
import server.interfaces.Server;
import server.models.ServerData;
import server.models.ServerImpl;

import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Test for the setQuizInactive method in serverImpl
 * Also tests validateActiveQuizID
 *
 * @author Michael Bragg
 */
public class SetQuizInactiveTest {
    private Server server;

    @Mock
    private ServerData data;
    @Mock
    private QuizFactory factory;
    @Mock
    private Quiz quiz;
    @Mock
    private Iterator<Game> gameIterator;
    @Mock
    private List<Game> games;

    @Before
    public void setUp() throws RemoteException {
        initMocks(this);
        this.server = new ServerImpl(factory, data);
    }

    // setQuizInactive
    @Test
    public void testSetQuizInactive() throws RemoteException {
        when(quiz.isActive()).thenReturn(true);
        when(data.getQuiz(10)).thenReturn(quiz);
        when(data.getGame(10)).thenReturn(games);
        when(games.iterator()).thenReturn(gameIterator);
        when(gameIterator.hasNext()).thenReturn(false);

        server.setQuizInactive(10);

        verify(data).getGame(10);
        verify(quiz).setActive(false);
    }

    @Test
    public void testSetQuizInactiveNullGames() throws RemoteException {
        when(quiz.isActive()).thenReturn(true);
        when(data.getQuiz(10)).thenReturn(quiz);
        when(data.getGame(10)).thenReturn(null);

        server.setQuizInactive(10);

        verify(data).getGame(10);
        verify(quiz).setActive(false);
    }
}
