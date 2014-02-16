package server.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import server.interfaces.Question;
import server.interfaces.Quiz;
import server.models.QuestionImpl;
import server.models.QuizImpl;


public class QuizImplTest {
	private  Quiz quiz;
	
	@Before
	public  void setUp() {
		quiz = new QuizImpl(0, "TestQuiz");
	}

	@Test
	public void testGetID() {
		int id = quiz.getQuizID();
		assertEquals(id, 0);
	}

	@Test
	public void testGetQuizName() {
		String actual = quiz.getQuizName();
		assertEquals("TestQuiz", actual);
	}

	@Test
	public void testGetEmptyQuestions() {
		List<Question> questions = quiz.getQuestions();
		assertTrue(questions.isEmpty());
	}
	
	@Test
	public void testGetQuestions() {
		Question question = new QuestionImpl(0, "quizQuestion");
		quiz.addQuestion(question);
		List<Question> questions = quiz.getQuestions();
		assertFalse(questions.isEmpty());
	}

	@Test
	public void testAddAndGetQuestion() {
		Question expected = new QuestionImpl(0, "quizQuestion");
		quiz.addQuestion(expected);
		Question actual = quiz.getQuestion(0);
		assertEquals(expected, actual);
	}
	@Test
	public void testAddAndGetQuestionNull() {
		Question actual = quiz.getQuestion(1);
		assertEquals(null, actual);
	}

}
