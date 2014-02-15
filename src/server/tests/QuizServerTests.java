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

	// Create new Quiz
	@Test
	public void CreateQuiztest() {
		this.server = new QuizServer();
		int quizID = server.createQuiz("test quiz");
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
	
	// Questions
	@Test
	public void addQuestionsToQuizTest() {
		int quizID = this.server.createQuiz("test quiz");
		int questionID = this.server.addQuestionToQuiz(quizID, "Some question");
		assertEquals(0, questionID);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addBlankQuestionsToQuizTest() {
		int quizID = this.server.createQuiz("");
		this.server.addQuestionToQuiz(quizID, "Some question");
	}
	
	// Answers
	@Test
	public void addAnswerTest() {
		int quizID = this.server.createQuiz("test quiz");
		int questionID = this.server.addQuestionToQuiz(quizID, "Some question");
		int answerID = this.server.addAnswerToQuestion(questionID, "some answer");
		assertEquals(0, answerID);
	}

}
