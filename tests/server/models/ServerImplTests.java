package server.models;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import server.factories.QuizFactory;
import server.interfaces.Game;
import server.interfaces.Question;
import server.interfaces.Quiz;
import server.interfaces.Server;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.anyListOf;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;


public class ServerImplTests {

    private Server server;

    @Mock
    private ServerData data;
    @Mock
    private Quiz quiz;
    @Mock
    private Question question;
    @Mock
    private QuizFactory factory;
    @Mock
    private Game game;
    @Mock
    private List<String> answers;
    @Mock
    private List<Question> questions;
    @Mock
    private List<Quiz> quizzes;
    @Mock
    private List<Game> games;
    @Mock
    private Iterator<Game> gameIterator;
    @Mock
    private Iterator<Quiz> quizIterator;
    @Mock
    private ConcurrentHashMap<Integer, Quiz> quizHashMap;
    @Mock
    private SimpleDateFormat dateFormat;



    @Before
    public void before() throws RemoteException {
        initMocks(this);

        //QuizFactory factory = QuizFactory.getInstance();
        //ServerData data = new ServerData();
        this.server = new ServerImpl(factory, data);
    }


    // createQuiz
    @Test
    public void testCreateQuiz() throws Exception {
        when(factory.getQuiz(anyInt(), anyString())).thenReturn(quiz);
        when(quiz.getQuizID()).thenReturn(10);

        int quizID = server.createQuiz("test quiz");

        verify(data).addQuiz(10, quiz);
        assertEquals(10, quizID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateQuizBlankName() throws Exception {
        server.createQuiz("");
    }

    @Test(expected = NullPointerException.class)
    public void testcreateQuizNullName() throws Exception {
        server.createQuiz(null);
    }


    // addQuestionToQuiz
    @Test
    public void testAddQuestionsToQuiz() throws Exception {
        when(factory.getQuestion(anyInt(), eq("test question"))).thenReturn(question);
        when(data.getQuiz(anyInt())).thenReturn(quiz);

        int questionID = server.addQuestionToQuiz(10, "test question");

        verify(factory).getQuestion(anyInt(), eq("test question"));
        verify(data).getQuiz(10);
        verify(quiz).addQuestion(question);

        assertEquals(0, questionID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddBlankQuestionsToQuiz() throws Exception {
        server.addQuestionToQuiz(10, "");
    }

    @Test(expected = NullPointerException.class)
    public void testAddBlankQuestionsToQuizNull() throws Exception {
        server.addQuestionToQuiz(99, "Some question");
    }


    // addAnswerToQuestion
    @Test
    public void testAddAnswerToQuestion() throws Exception {
        when(data.getQuiz(10)).thenReturn(quiz);
        when(quiz.getQuestion(20)).thenReturn(question);

        server.addAnswerToQuestion(10, 20, "some answer");

        verify(data, times(2)).getQuiz(anyInt());
        verify(quiz, times(2)).getQuestion(anyInt());
        verify(question).addAnswer("some answer");
    }

    @Test(expected = NullPointerException.class)
    public void testAddAnswerToQuestionUnknownQuestionID() throws Exception {
        when(data.getQuiz(10)).thenReturn(quiz);
        when(quiz.getQuestion(20)).thenReturn(null);
        server.addAnswerToQuestion(10, 20, "some answer");
    }

    @Test(expected = NullPointerException.class)
    public void testAddAnswerToQuestionUnknownQuizID() throws Exception {
        when(data.getQuiz(10)).thenReturn(null);
        server.addAnswerToQuestion(10, 20, "some answer");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddBlankAnswer() throws Exception {
        server.addAnswerToQuestion(10, 20, "");
    }


    // setCorrectAnswer
    @Test
    public void testSetCorrectAnswer() throws RemoteException {
        when(data.getQuiz(10)).thenReturn(quiz);
        when(quiz.getQuestion(20)).thenReturn(question);
        when(question.getAnswers()).thenReturn(answers);
        when(answers.size()).thenReturn(31);

        server.setCorrectAnswer(10, 20, 30);

        verify(question).setCorrectAnswerID(30);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetCorrectAnswerTooLarge() throws RemoteException {
        when(data.getQuiz(10)).thenReturn(quiz);
        when(quiz.getQuestion(20)).thenReturn(question);
        when(question.getAnswers()).thenReturn(answers);
        when(answers.size()).thenReturn(30);

        server.setCorrectAnswer(10, 20, 30);

        verify(question).setCorrectAnswerID(30);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetCorrectAnswerTooSmall() throws RemoteException {
        when(data.getQuiz(10)).thenReturn(quiz);
        when(quiz.getQuestion(20)).thenReturn(question);
        when(question.getAnswers()).thenReturn(answers);
        when(answers.size()).thenReturn(30);

        server.setCorrectAnswer(10, 20, -1);

        verify(question).setCorrectAnswerID(30);
    }


    // getQuizQuestionsAndAnswers
    @Test
    public void testGetQuizQuestionsAndAnswers() throws Exception {
        when(data.getQuiz(10)).thenReturn(quiz);
        when(quiz.getQuestions()).thenReturn(questions);

        server.getQuizQuestionsAndAnswers(10);

        verify(quiz).getQuestions();
    }


    // getAnswersForQuestion
    @Test
    public void testGetAnswersForQuestion() throws Exception {
        when(data.getQuiz(10)).thenReturn(quiz);
        when(quiz.getQuestion(20)).thenReturn(question);
        when(question.getAnswers()).thenReturn(answers);

        server.getAnswersForQuestion(10, 20);

        verify(question).getAnswers();
    }


    // setQuizActive
    @Test
    public void testSetQuizActive() throws RemoteException {
        when(data.getQuiz(10)).thenReturn(quiz);

        server.setQuizActive(10);

        verify(quiz).setActive(true);
    }

    @Test(expected = NullPointerException.class)
    public void testSetQuizActiveException() throws RemoteException {
        when(data.getQuiz(10)).thenReturn(quiz);
        when(quiz.isActive()).thenReturn(true);

        server.setQuizActive(10);
    }


    // getActiveQuizzes
    @Test
    public void testGetActiveQuizzes() throws RemoteException {
        when(quiz.isActive()).thenReturn(true);
        quizzes.add(0, quiz);

        when(data.getQuizzes()).thenReturn(quizHashMap);
        when(quizHashMap.values()).thenReturn(quizzes);

        when(quizzes.iterator()).thenReturn(quizIterator);
        when(quizIterator.hasNext()).thenReturn(true).thenReturn(false);
        when(quizIterator.next()).thenReturn(quiz);

        List<Quiz> result = server.getActiveQuizzes();
        assertEquals(1, result.size());
    }

    // getInactiveQuizzes
    @Test
    public void testgetInactiveQuizzes() throws RemoteException {
        when(quiz.isActive()).thenReturn(false);
        quizzes.add(0, quiz);

        when(data.getQuizzes()).thenReturn(quizHashMap);
        when(quizHashMap.values()).thenReturn(quizzes);

        when(quizzes.iterator()).thenReturn(quizIterator);
        when(quizIterator.hasNext()).thenReturn(true).thenReturn(false);
        when(quizIterator.next()).thenReturn(quiz);

        List<Quiz> result = server.getInactiveQuizzes();
        assertEquals(1, result.size());
    }

    // setQuizInactive
    @Test
    public void testSetQuizInactive() throws RemoteException {
        when(quiz.isActive()).thenReturn(true);
        when(data.getQuiz(10)).thenReturn(quiz);
        when(data.getGame(10)).thenReturn(games);
        when(games.iterator()).thenReturn(gameIterator);
        when(gameIterator.hasNext()).thenReturn(false);

        server.setQuizInactive(10);

        verify(data).getGame(10);
        verify(quiz).setActive(false);
    }

    @Test
    public void testSetQuizInactiveNullGames() throws RemoteException {
        when(quiz.isActive()).thenReturn(true);
        when(data.getQuiz(10)).thenReturn(quiz);
        when(data.getGame(10)).thenReturn(null);

        server.setQuizInactive(10);

        verify(data).getGame(10);
        verify(quiz).setActive(false);
    }


    // getHighScoreGames
    @Test
    public void testGetHighScoreGames() throws RemoteException {
        when(game.getScore()).thenReturn(10);
        when(games.iterator()).thenReturn(gameIterator);
        when(gameIterator.hasNext()).thenReturn(true, false, true, false);
        when(gameIterator.next()).thenReturn(game, game);

        List<Game> result = server.getHighScoreGames(games);
        System.out.println(result.size());
        assertEquals(1, result.size());
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


    // submitScore
    @Test
    public void testSubmitScore() throws RemoteException {
        when(data.getQuiz(10)).thenReturn(quiz);

        //when(factory.getDate()).thenCallRealMethod();
        //when(dateFormat.format(any(Date.class))).thenCallRealMethod();

        when(game.getGameID()).thenReturn(20);
        when(data.getGame(10)).thenReturn(games);
        when(games.iterator()).thenReturn(gameIterator);
        when(gameIterator.hasNext()).thenReturn(true, false, true, false);
        when(gameIterator.next()).thenReturn(game, game);
        when(quiz.isActive()).thenReturn(true);

        server.submitScore(10, 20, 30);

        //verify(game).setPlayerScoreWithDate(eq(30), anyString());
    }




}
