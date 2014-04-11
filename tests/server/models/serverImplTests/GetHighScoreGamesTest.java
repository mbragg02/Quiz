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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Test for the getHighScoreGames method in serverImpl
 *
 * @author Michael Bragg
 */
public class GetHighScoreGamesTest {

    private Server server;

    @Mock
    private ServerData data;
    @Mock
    private QuizFactory factory;
    @Mock
    private Quiz quiz;
    @Mock
    private List<Game> games;
    @Mock
    private Iterator<Game> gameIterator;
    @Mock
    private Game game;


    @Before
    public void setUp() throws Exception {
        initMocks(this);
        this.server = new ServerImpl(factory, data);

    }

    // getHighScoreGames
    @Test
    public void testGetHighScoreGames() throws RemoteException {
        when(game.getScore()).thenReturn(10);
        when(games.iterator()).thenReturn(gameIterator);
        when(gameIterator.hasNext()).thenReturn(true, false, true, false);
        when(gameIterator.next()).thenReturn(game, game);

        List<Game> result = server.getHighScoreGames(games);
        System.out.println(result.size());
        assertEquals(1, result.size());
    }
}
