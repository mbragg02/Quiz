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

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Test for the submitScore method in serverImpl
 * Also tests validateQuizAndGameID
 *
 * @author Michael Bragg
 */
public class SubmitScoreTest {

    private Server server;

    @Mock
    private ServerData data;
    @Mock
    private Quiz quiz;
    @Mock
    private List<Game> games;
    @Mock
    private Game game;
    @Mock
    private Iterator<Game> gameIterator;


    @Before
    public void setUp() throws Exception {
        initMocks(this);

        // Factory is not mocked because its used to get a new Date. Which is then in turn passed to a SimpleDateFormat.
        // Spy used to call real methods
        QuizFactory factory = QuizFactory.getInstance();
        QuizFactory spyFactory = spy(factory);

        this.server = new ServerImpl(spyFactory, data);
    }

    // submitScore

    // Default submit score flow
    @Test
    public void testSubmitScore() throws RemoteException {
        when(data.getQuiz(10)).thenReturn(quiz);
        when(quiz.isActive()).thenReturn(true);

        when(game.getGameID()).thenReturn(20);
        when(data.getGame(10)).thenReturn(games);

        when(games.iterator()).thenReturn(gameIterator);
        when(gameIterator.hasNext()).thenReturn(true, false, true, false);
        when(gameIterator.next()).thenReturn(game, game);

        server.submitScore(10, 20, 30);

        verify(game).setPlayerScoreWithDate(eq(30), anyString());
    }

    //validateQuizAndGameID
    @Test(expected = NullPointerException.class)
    public void testValidateQuizAndGameIDNullGamesList() throws RemoteException {
        when(data.getGame(10)).thenReturn(null);

        server.submitScore(10, 20, 30);
    }

    @Test(expected = NullPointerException.class)
    public void testValidateQuizAndGameIDNullGame() throws RemoteException {
        when(data.getGame(10)).thenReturn(games);
        when(games.iterator()).thenReturn(gameIterator);
        when(gameIterator.hasNext()).thenReturn(false);

        server.submitScore(10, 20, 30);
    }

}
