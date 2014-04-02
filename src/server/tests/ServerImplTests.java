
import org.junit.Before;
import org.junit.Test;
import server.Factories.QuizFactory;
import server.interfaces.Question;
import server.interfaces.Quiz;
import server.interfaces.Server;
import server.models.ServerData;
import server.models.ServerImpl;

import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class ServerImplTests {

    private Server server;

    @Before
    public void before() throws RemoteException {
        QuizFactory factory = QuizFactory.getInstance();
        Logger testLogger = Logger.getLogger("TestLogger");
        ServerData testData = new ServerData();
        this.server = new ServerImpl(factory, testLogger, testData);
    }

    // Create new Quiz
    @Test
    public void createQuiztest() throws Exception {
        int quizID = server.createQuiz("test quiz");
        assertEquals(0, quizID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createQuizBlankNametest() throws Exception {
        server.createQuiz("");
    }

    @Test(expected = NullPointerException.class)
    public void createQuizNullNametest() throws Exception {
        server.createQuiz(null);
    }

    // Questions
    @Test
    public void addQuestionsToQuizTest() throws Exception {
        int quizID = server.createQuiz("test quiz");
        int questionID = server.addQuestionToQuiz(quizID, "Some question");
        assertEquals(0, questionID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addBlankQuestionsToQuizTest() throws Exception {
        int quizID = server.createQuiz("test quiz");
        server.addQuestionToQuiz(quizID, "");
    }

    @Test(expected = NullPointerException.class)
    public void addBlankQuestionsToQuizTestNull() throws Exception {
        server.addQuestionToQuiz(99, "Some question");
    }

    @Test
    public void setQuestionCorrectAnswerTest() throws Exception {
        int quizID = server.createQuiz("test quiz");
        int questionID = server.addQuestionToQuiz(quizID, "Some question");
        server.addAnswerToQuestion(quizID, questionID, "some answer");
        server.setCorrectAnswer(quizID, questionID, 0);
        // No exception so passed
    }

    @Test(expected = NullPointerException.class)
    public void setQuestionCorrectAnswerTestUnknownQuestionID() throws Exception {
        int quizID = server.createQuiz("test quiz");
        int questionID = server.addQuestionToQuiz(quizID, "Some question");
        server.addAnswerToQuestion(quizID, questionID, "some answer");
        server.setCorrectAnswer(quizID, 99, 0);
    }

    @Test(expected = NullPointerException.class)
    public void setQuestionCorrectAnswerTestUnknownQuizID() throws Exception {
        int quizID = server.createQuiz("test quiz");
        int questionID = server.addQuestionToQuiz(quizID, "Some question");
        server.addAnswerToQuestion(quizID, questionID, "some answer");
        server.setCorrectAnswer(99, questionID, 0);
    }


    // Answers
    @Test
    public void addAnswerTest() throws Exception {
        int quizID = server.createQuiz("test quiz");
        int questionID = server.addQuestionToQuiz(quizID, "Some question");
        server.addAnswerToQuestion(quizID, questionID, "some answer");
        assertEquals(0, 0);
    }

    @Test(expected = NullPointerException.class)
    public void addAnswerTestUnknownQuestionID() throws Exception {
        int quizID = server.createQuiz("test quiz");
        server.addAnswerToQuestion(quizID, 999, "some answer");
    }

    @Test(expected = NullPointerException.class)
    public void addAnswerTestUnknownQuizID() throws Exception {
        int quizID = server.createQuiz("test quiz");
        int questionID = server.addQuestionToQuiz(quizID, "Some question");
        server.addAnswerToQuestion(999, questionID, "some answer");
    }

    @Test(expected = IllegalArgumentException.class)
    public void addBlankAnswerTest() throws Exception {
        int quizID = server.createQuiz("test quiz");
        int questionID = server.addQuestionToQuiz(quizID, "Some question");
        server.addAnswerToQuestion(quizID, questionID, "");

    }

    // getQuestionsAndAnswers
    @Test
    public void getQuestionsAndAnswersTest() throws Exception {
        int quizID = server.createQuiz("test quiz");

        List<Question> result = server.getQuizQuestionsAndAnswers(quizID);
        assertTrue(result.isEmpty());

        int questionID = server.addQuestionToQuiz(quizID, "Some question");
        server.addAnswerToQuestion(quizID, questionID, "some answer 1");
        result = server.getQuizQuestionsAndAnswers(quizID);
        assertFalse(result.isEmpty());
    }

    @Test(expected = NullPointerException.class)
    public void getQuestionsAndAnswersTestNullPointer() throws Exception {
        server.getQuizQuestionsAndAnswers(999);
    }


    // Active quizes
    @Test
    public void setAndGetQuizActiveTest() throws Exception {
        int quizID = server.createQuiz("test quiz 1");
        server.setQuizActive(quizID);
        List<Quiz> active = server.getActiveQuizzes();
        assertEquals(quizID, active.iterator().next().getQuizID());
    }

    @Test
    public void setQuizInactiveTest() throws Exception {
        int quizID = server.createQuiz("test quiz 1");

        List<Quiz> active = server.getActiveQuizzes();
        assertTrue(active.isEmpty());

        server.setQuizActive(quizID);
        active = server.getActiveQuizzes();
        assertFalse(active.isEmpty());

        server.setQuizInactive(quizID);
        active = server.getActiveQuizzes();
        assertTrue(active.isEmpty());
    }


    //Games
    @Test
    public void startGameTest() throws Exception {
        int quizID = server.createQuiz("test quiz");
        server.setQuizActive(quizID);
        server.startGame(quizID, "Michael");
    }

    @Test(expected = NullPointerException.class)
    public void startGameTestNoQuiz() throws Exception {
        server.startGame(0, "Michael");
    }

    @Test(expected = IllegalArgumentException.class)
    public void startGameTestBlankName() throws Exception {
        int quizID = server.createQuiz("test quiz");
        server.startGame(quizID, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void startGameTestInactive() throws Exception {
        int quizID = server.createQuiz("test quiz");
        server.startGame(quizID, "Michael");
    }

    //Scores
    @Test
    public void submitScoreTest() throws Exception {
        int quizID = server.createQuiz("test quiz");
        server.setQuizActive(quizID);
        int gameID = server.startGame(quizID, "Michael");
        server.submitScore(quizID, gameID, 10);
    }

    @Test(expected = NullPointerException.class)
    public void submitScoreTestNullGame() throws Exception {
        int quizID = server.createQuiz("test quiz");
        server.setQuizActive(quizID);
        int gameID = server.startGame(quizID, "Michael");
        server.submitScore(999, gameID, 10);
    }

    @Test(expected = NullPointerException.class)
    public void submitScoreTestNullQuiz() throws Exception {
        int quizID = server.createQuiz("test quiz");
        server.setQuizActive(quizID);
        int gameID = server.startGame(999, "Michael");
        server.submitScore(quizID, gameID, 10);
    }

}
