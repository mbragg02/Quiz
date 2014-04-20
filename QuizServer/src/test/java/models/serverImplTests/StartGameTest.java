package models.serverImplTests;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import factories.QuizFactory;
import interfaces.Game;
import interfaces.Question;
import interfaces.Quiz;
import interfaces.Server;
import models.ServerData;
import models.ServerImpl;

import java.rmi.RemoteException;
import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Test for the startGame method in serverImpl
 *
 * @author Michael Bragg
 */
public class StartGameTest {

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
    private Game game;
    @Mock
    private List<Question> questions;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        this.server = new ServerImpl(factory, data);
    }

    // startGame
    @Test
    public void testStartGame() throws RemoteException {
        when(factory.getGame(anyInt(), eq("michael"))).thenReturn(game);
        when(quiz.isActive()).thenReturn(true);
        when(data.getQuiz(10)).thenReturn(quiz);
        when(quiz.getQuestions()).thenReturn(questions);
        when(questions.size()).thenReturn(20);
        when(data.getGame(10)).thenReturn(games);

        server.startGame(10, "michael");

        verify(game).setNumberOfQuestions(20);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStartGameBlankName() throws RemoteException {
        server.startGame(10, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStartGameNameStartsWithADigit() throws RemoteException {
        server.startGame(10, "4michael");
    }

    @Test
    public void testStartGameForTheFirstTime() throws RemoteException {
        when(factory.getGame(anyInt(), eq("michael"))).thenReturn(game);
        when(quiz.isActive()).thenReturn(true);
        when(data.getQuiz(10)).thenReturn(quiz);
        when(quiz.getQuestions()).thenReturn(questions);
        when(questions.size()).thenReturn(20);
        when(data.getGame(10)).thenReturn(null);

        server.startGame(10, "michael");

        verify(game).setNumberOfQuestions(20);
        verify(data).addGame(eq(10), anyListOf(Game.class));
    }
}
