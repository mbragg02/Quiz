package server.tests;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import server.Answer;
import server.Question;
import server.Quiz;
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
		int quizID = this.server.createQuiz("test quiz");
		this.server.addQuestionToQuiz(quizID, "");
	}
	
	@Test(expected = NullPointerException.class)
	public void addBlankQuestionsToQuizTestNull() {
		this.server.addQuestionToQuiz(99, "Some question");
	}
	
	@Test
	public void setQuestionCorrectAnswerTest() {
		int quizID = this.server.createQuiz("test quiz");
		int questionID = this.server.addQuestionToQuiz(quizID, "Some question");
		int answerID = this.server.addAnswerToQuestion(questionID, "some answer");
		this.server.setCorrectAnswer(questionID, answerID);
		// assertEquals(answerID, actual);
	}
	@Test(expected = NullPointerException.class)
	public void setQuestionCorrectAnswerTestNullAnswerID() {
		int quizID = this.server.createQuiz("test quiz");
		int questionID = this.server.addQuestionToQuiz(quizID, "Some question");
		this.server.setCorrectAnswer(questionID, 99);
	}
	@Test(expected = NullPointerException.class)
	public void setQuestionCorrectAnswerTestNullQuestionID() {
		int quizID = this.server.createQuiz("test quiz");
		int questionID = this.server.addQuestionToQuiz(quizID, "Some question");
		int answerID = this.server.addAnswerToQuestion(questionID, "some answer");
		this.server.setCorrectAnswer(99, answerID);
	}
	
	// Answers
	@Test
	public void addAnswerTest() {
		int quizID = this.server.createQuiz("test quiz");
		int questionID = this.server.addQuestionToQuiz(quizID, "Some question");
		int answerID = this.server.addAnswerToQuestion(questionID, "some answer");
		assertEquals(0, answerID);
	}
	@Test(expected = NullPointerException.class)
	public void addAnswerTestNull() {
		this.server.addAnswerToQuestion(999, "some answer");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addBlankAnswerTest() {
		int quizID = this.server.createQuiz("test quiz");
		int questionID = this.server.addQuestionToQuiz(quizID, "Some question");
		this.server.addAnswerToQuestion(questionID, "");
	
	}
	
	// getQuestionsAndAnswers Map
	@Test
	public void getQuestionsAndAnswersTest() {
		int quizID1 = this.server.createQuiz("test quiz 1");
		int questionID1 = this.server.addQuestionToQuiz(quizID1, "Some question 1");
		this.server.addAnswerToQuestion(questionID1, "some answer 1");
		
		int quizID2 = this.server.createQuiz("test quiz 2");
		int questionID2 = this.server.addQuestionToQuiz(quizID2, "Some question 2");
		this.server.addAnswerToQuestion(questionID2, "some answer 2");
		
		Map<Question, List<Answer>> result = this.server.getQuestionsAndAnswers(quizID1);
		
		for (Entry<Question, List<Answer>> entry : result.entrySet()) {
			assertEquals("Some question 1", entry.getKey().getQuestion());
			for (Answer answer: entry.getValue()) {
				assertEquals("some answer 1", answer.getAnswer());
			}	
		}
	}
	
	
	// Active quizes
	@Test 
	public void setAndGetQuizActiveTest() {
		int quizID = this.server.createQuiz("test quiz 1");
		this.server.setQuizActive(quizID);
		List<Quiz> active = this.server.getActiveQuizes();
		assertEquals(quizID, active.iterator().next().getQuizID());
	}
	
	@Test
	public void setQuizInactiveTest() {
		int quizID = this.server.createQuiz("test quiz 1");
		
		List<Quiz> active = this.server.getActiveQuizes();
		assertTrue(active.isEmpty());
		
		this.server.setQuizActive(quizID);
		active = this.server.getActiveQuizes();
		assertFalse(active.isEmpty());
		
		this.server.setQuizInactive(quizID);
		active = this.server.getActiveQuizes();
		assertTrue(active.isEmpty());	
	}
	
	
	//Games
	@Test
	public void startGameTest() {
		int gameID = this.server.startGame(0, "Michael");
	}

}
