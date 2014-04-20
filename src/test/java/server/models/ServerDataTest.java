package server.models;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import server.interfaces.Game;
import server.interfaces.Quiz;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Tests for the ServerData class
 *
 * @author Michael Bragg
 */
public class ServerDataTest {

    private ServerData data;

    @Mock
    private Quiz quiz;
    @Mock
    private List<Game> gameList;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        data = new ServerData();
    }

    @Test
    public void testGetQuizzes() throws Exception {
        ConcurrentMap<Integer, Quiz> result = data.getQuizzes();

        assertTrue(result.isEmpty());
    }

    @Test
    public void testAddQuiz() throws Exception {
        data.addQuiz(10, quiz);
        ConcurrentMap<Integer, Quiz> result = data.getQuizzes();

        assertFalse(result.isEmpty());
    }

    @Test
    public void testAddGame() throws Exception {
        data.addGame(10, gameList);
        List<Game> result = data.getGame(10);

        assertFalse(result.isEmpty());
    }

    @Test
    public void testGetQuiz() throws Exception {
        data.addQuiz(10, quiz);
        Quiz result = data.getQuiz(10);

        assertEquals(quiz, result);
    }

    @Test(expected = NullPointerException.class)
    public void testGetQuizException() throws Exception {
        data.getQuiz(10);
    }

    @Test
    public void testGetGameEmpty() throws Exception {
        assertNull(data.getGame(10));
    }

    @Test
    public void testGetGameNonEmpty() throws Exception {
        data.addGame(10, gameList);
        List<Game> result = data.getGame(10);

        assertFalse(result.isEmpty());
    }

    @Test
    public void testGetQuizID() throws Exception {
        int result1 = data.getQuizID();
        assertEquals(0, result1);

        int result2 = data.getQuizID();
        assertEquals(1, result2);
    }

    @Test
    public void testGetQuestionID() throws Exception {
        int result1 = data.getQuestionID();
        assertEquals(0, result1);

        int result2 = data.getQuestionID();
        assertEquals(1, result2);
    }

    @Test
    public void testGetGameID() throws Exception {
        int result1 = data.getGameID();
        assertEquals(0, result1);

        int result2 = data.getGameID();
        assertEquals(1, result2);
    }
}
