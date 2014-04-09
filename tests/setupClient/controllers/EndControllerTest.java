package setupClient.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import server.interfaces.Game;
import server.interfaces.Quiz;
import server.interfaces.Server;
import setupClient.views.EndQuizView;

import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Michael Bragg
 *         Tests for the EndQuiz class for the setup client.
 */
public class EndControllerTest {

    public static final String TEST_NAME = "Test Name";
    public static final String TEST_DATE = "Test Date";
    private EndController client;

    @Mock
    private Server model;
    @Mock
    private EndQuizView view;
    @Mock
    private List<Quiz> nonEmptyListOfQuizzes;
    @Mock
    private List<Quiz> emptyListOfquizzes;
    @Mock
    private Quiz quiz;
    @Mock
    private List<Game> nonEmptyListOfGames;
    @Mock
    private List<Game> emptyListOfGames;
    @Mock
    private Game game;
    @Mock
    private Iterator<Game> gameIterator;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        // Setup mock game getters return values
        when(game.getDateCompleted()).thenReturn(TEST_DATE);
        when(game.getPlayerName()).thenReturn(TEST_NAME);

        // Setup mock empty/non-empty quiz lists
        nonEmptyListOfQuizzes.add(0, quiz);
        when(emptyListOfquizzes.isEmpty()).thenReturn(true);

        // Setup mock empty/non-empty game lists
        nonEmptyListOfGames.add(0, game);
        when(emptyListOfGames.isEmpty()).thenReturn(true);

        when(view.getNextIntFromConsole()).thenReturn(10);

        client = new EndController(model, view);
    }

    /*
    Tests calling a view message if there are no active quizzes
     */
    @Test
    public void testLaunchNoActiveQuizzes() throws Exception {
        when(model.getActiveQuizzes()).thenReturn(emptyListOfquizzes);

        client.launch();

        verify(view).printNoActiveQuizesMessage();
    }

    /*
    Tests calling a view message if there where no games that tooke place for a Quiz.
     */
    @Test
    public void testLaunchNoPlayers() throws RemoteException {
        when(model.getActiveQuizzes()).thenReturn(nonEmptyListOfQuizzes);
        when(model.setQuizInactive(anyInt())).thenReturn(emptyListOfGames);

        client.launch();

        verify(view).printQuizIDDeactivationRequest();
        verify(view).getNextIntFromConsole();
        verify(view).getNextLineFromConsole();
        verify(view).printNoPlayersMessage();
    }

    /*
    Default flow for displaying a winners details
     */
    @Test
    public void testLaunch() throws RemoteException {
        when(model.getActiveQuizzes()).thenReturn(nonEmptyListOfQuizzes);
        when(model.setQuizInactive(anyInt())).thenReturn(nonEmptyListOfGames);

        // Setup mock quiz iterator behaviour
        when(nonEmptyListOfGames.iterator()).thenReturn(gameIterator);
        when(gameIterator.hasNext()).thenReturn(true).thenReturn(false);
        when(gameIterator.next()).thenReturn(game);

        client.launch();

        verify(view).printQuizIDDeactivationRequest();
        verify(view).getNextIntFromConsole();
        verify(view).getNextLineFromConsole();
        verify(view).printWinnersAreMessage();
        verify(view).printWinnerDetails(eq(1), eq(TEST_NAME), anyDouble(), eq(TEST_DATE));

    }
}
