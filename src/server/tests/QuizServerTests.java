package server.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import server.QuizServer;

public class QuizServerTests {
	
	

	@Test
	public void CreateQuiztest() {
		QuizServer server = new QuizServer();
		int quizID = server.createQuiz("test Quiz");
		assertEquals(0, quizID);
	}

}
