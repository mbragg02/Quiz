package server.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import server.interfaces.Game;
import server.models.GameImpl;


public class GameImplTest {
	private Game game;

	@Before
	public void setUp() throws Exception {
		this.game = new GameImpl(0, "testName");
	}

	@Test
	public void testGameImpl() {
		Game game = new GameImpl(1, "testName");
		assertEquals(1, game.getGameID());
		assertEquals("testName", game.getPlayerName());
	}

	@Test
	public void testGetGameId() {
		assertEquals(0, game.getGameID());
	}

	@Test
	public void testGetScore() {
		game.setPlayerScoreWithDate(5, "some date");
		assertEquals(5, game.getScore());
	}

	@Test
	public void testSetPlayerScoreWithDate() {
		game.setPlayerScoreWithDate(5, "some date");
		assertFalse(game.getScore() == 0);
	}

	@Test
	public void testGetNumberOfQuestions() {
		assertEquals(0, game.getNumberOfQuestions());
	}

	@Test
	public void testSetNumberOfQuestions() {
		game.setNumberOfQuestions(10);
		assertEquals(10, game.getNumberOfQuestions());
	}

	@Test
	public void testGetPlayerName() {
		assertEquals("testName", game.getPlayerName());
	}

	@Test
	public void testGetDateCompleted() {
		game.setPlayerScoreWithDate(5, "some date");
		assertEquals("some date", game.getDateCompleted());

	}

}
