package server.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import server.QuizServer;

public class QuizServerTests {
	
	private QuizServer server;
	@Before
	public void before() {
		this.server = new QuizServer();
	}

	@Test
	public void CreateQuiztest() {
		this.server = new QuizServer();
		int quizID = server.createQuiz("test Quiz");
		assertEquals(0, quizID);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void CreateQuizBlankNametest() {
		this.server = new QuizServer();
		server.createQuiz("");
	}
	@Test(expected = NullPointerException.class)
	public void CreateQuizNullNametest() {
		this.server = new QuizServer();
		server.createQuiz(null);
	}

}
