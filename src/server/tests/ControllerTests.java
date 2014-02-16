package server.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import server.interfaces.Question;
import server.interfaces.Quiz;
import server.interfaces.Server;
import server.models.ServerImpl;

public class ControllerTests {
	
	private Server server;
	@Before
	public void before() {
		this.server = new ServerImpl();
	}

	// Create new Quiz
	@Test
	public void CreateQuiztest() {
		this.server = new ServerImpl();
		int quizID = server.createQuiz("test quiz");
		assertEquals(0, quizID);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void CreateQuizBlankNametest() {
		this.server = new ServerImpl();
		server.createQuiz("");
	}
	@Test(expected = NullPointerException.class)
	public void CreateQuizNullNametest() {
		this.server = new ServerImpl();
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
		this.server.addAnswerToQuestion(quizID, questionID, "some answer");
//		this.server.setCorrectAnswer(questionID, answerID);
		// assertEquals(answerID, actual);
	}

	@Test(expected = NullPointerException.class)
	public void setQuestionCorrectAnswerTestNullQuestionID() {
		int quizID = this.server.createQuiz("test quiz");
		int questionID = this.server.addQuestionToQuiz(quizID, "Some question");
		this.server.addAnswerToQuestion(quizID, questionID, "some answer");
		this.server.setCorrectAnswer(quizID, 99, 0);
	}
	
	// Answers
	@Test
	public void addAnswerTest() {
		int quizID = this.server.createQuiz("test quiz");
		int questionID = this.server.addQuestionToQuiz(quizID, "Some question");
		this.server.addAnswerToQuestion(quizID, questionID, "some answer");
		assertEquals(0, 0);
	}
	@Test(expected = NullPointerException.class)
	public void addAnswerTestNull() {
		int quizID = this.server.createQuiz("test quiz");
		this.server.addAnswerToQuestion(quizID, 999, "some answer");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addBlankAnswerTest() {
		int quizID = this.server.createQuiz("test quiz");
		int questionID = this.server.addQuestionToQuiz(quizID, "Some question");
		this.server.addAnswerToQuestion(quizID, questionID, "");
	
	}
	
	// getQuestionsAndAnswers Map
	@Test
	public void getQuestionsAndAnswersTest() {
		int quizID1 = this.server.createQuiz("test quiz 1");
		int questionID1 = this.server.addQuestionToQuiz(quizID1, "Some question 1");
		this.server.addAnswerToQuestion(quizID1, questionID1, "some answer 1");
		
		int quizID2 = this.server.createQuiz("test quiz 2");
		int questionID2 = this.server.addQuestionToQuiz(quizID2, "Some question 2");
		this.server.addAnswerToQuestion(quizID2, questionID2, "some answer 2");
		
		List<Question> result = this.server.getQuizQuestionsAndAnswers(quizID1);
		
//		for (Entry<Question, List<Answer>> entry : result.entrySet()) {
//			assertEquals("Some question 1", entry.getKey().getQuestion());
//			for (Answer answer: entry.getValue()) {
//				assertEquals("some answer 1", answer.getAnswer());
//			}	
//		}
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
		int quizID = this.server.createQuiz("test quiz");
		this.server.setQuizActive(quizID);
		this.server.startGame(quizID, "Michael");
	}
	
	@Test(expected = NullPointerException.class)
	public void startGameTestNoQuiz() {
		this.server.startGame(0, "Michael");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void startGameTestBlankName() {
		int quizID = this.server.createQuiz("test quiz");
		this.server.startGame(quizID, "");
	}
	@Test(expected = IllegalArgumentException.class)
	public void startGameTestInactive() {
		int quizID = this.server.createQuiz("test quiz");
		this.server.startGame(quizID, "Michael");
	}
	
	//Scores
	@Test
	public void submitScoreTest() {
		int quizID = this.server.createQuiz("test quiz");
		this.server.setQuizActive(quizID);
		int gameID = this.server.startGame(quizID, "Michael");
		this.server.submitScore(quizID, gameID, 10);
	}
	@Test(expected = NullPointerException.class)
	public void submitScoreTestNullGame() {
		int quizID = this.server.createQuiz("test quiz");
		this.server.setQuizActive(quizID);
		int gameID = this.server.startGame(quizID, "Michael");
		this.server.submitScore(999, gameID, 10);
	}
	@Test(expected = NullPointerException.class)
	public void submitScoreTestNullQuiz() {
		int quizID = this.server.createQuiz("test quiz");
		this.server.setQuizActive(quizID);
		int gameID = this.server.startGame(999, "Michael");
		this.server.submitScore(quizID, gameID, 10);
	}

}
